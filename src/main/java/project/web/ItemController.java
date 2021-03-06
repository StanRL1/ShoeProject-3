package project.web;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.model.bindings.ItemAddBindingModel;
import project.model.services.ItemServiceModel;
import project.model.view.ItemViewModel;
import project.service.CartService;
import project.service.FrontPageService;
import project.service.ItemService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/items")
public class ItemController {
    private final ModelMapper modelMapper;
    private final ItemService itemService;
    private final FrontPageService frontPageService;
    private final CartService cartService;
    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Autowired



    public ItemController(ModelMapper modelMapper, ItemService itemService, FrontPageService frontPageService, CartService cartService) {
        this.modelMapper = modelMapper;
        this.itemService = itemService;
        this.frontPageService = frontPageService;
        this.cartService = cartService;
    }

    @ModelAttribute("itemAddBindingModel")
    public ItemAddBindingModel createBindingModel() {
        return new ItemAddBindingModel();
    }

    @GetMapping("/add")
    public String add(){
        return "add-item";
    }

    @PostMapping("/add")
    public String modelAndView(@Valid ItemAddBindingModel itemAddBindingModel, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("itemAddBindingModel", itemAddBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.itemAddBindingModel", bindingResult);
            return "redirect:/items/add";
        }
         ItemServiceModel itemServiceModel = modelMapper.map(
                itemAddBindingModel,
                ItemServiceModel.class);
        itemServiceModel.setAddedBy(principal.getUsername());
        itemService.createItem(itemServiceModel);

        LOGGER.info(String.valueOf(itemService.findAllItems().size()));
        return "redirect:/home";
    }
    @GetMapping("/all")
    public ModelAndView all(ModelAndView modelAndView){

        List<ItemViewModel> itemViewModelList=this.itemService.findAllItems().stream().map(item->{
            ItemViewModel itemViewModel= this.modelMapper.map(item,ItemViewModel.class);
            return itemViewModel;
        }).collect(Collectors.toList()); ;
        modelAndView.setViewName("all-items");
        modelAndView.addObject("count",itemViewModelList.size());
        modelAndView.addObject("items",itemViewModelList);
        return modelAndView;
    }

    @GetMapping("/my-items")
    public ModelAndView myItems(ModelAndView modelAndView,@AuthenticationPrincipal UserDetails principal){

        List<ItemViewModel> itemViewModelList=this.itemService.findAllItemsForUser(principal.getUsername()).stream().map(item-> this.modelMapper.map(item,ItemViewModel.class)).collect(Collectors.toList()); ;
        modelAndView.setViewName("my-items");
        modelAndView.addObject("count",itemViewModelList.size());
        modelAndView.addObject("items",itemViewModelList);
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") Long id,ModelAndView modelAndView,@AuthenticationPrincipal UserDetails principal){
        modelAndView.addObject("item",this.modelMapper.map(this.itemService.findById(id),ItemViewModel.class));
        modelAndView.setViewName("details-item");
        modelAndView.addObject("username",principal.getUsername());
        return modelAndView;
    }

    @GetMapping("/details")
    public ModelAndView detailsFrontPage(@RequestParam("id") Long id,ModelAndView modelAndView,@AuthenticationPrincipal UserDetails principal){
        modelAndView.addObject("item",this.modelMapper.map(this.itemService.findById(id),ItemViewModel.class));
        modelAndView.setViewName("details-item");
        modelAndView.addObject("username",principal.getUsername());
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        this.itemService.deleteById(id);
        this.frontPageService.reload();
        this.frontPageService.init();

            return "redirect:/items/my-items";
    }
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id, HttpSession session){
        cartService.addToCart(id,session);
        return "redirect:/users/profile";
    }
    @GetMapping("/buy/{id}")
    public String buyItem(@PathVariable("id") Long id, HttpSession session){
        cartService.delete(id,session);
        return "redirect:/users/profile";
    }

    @GetMapping("/buyAll")
    public String buyAll(HttpSession session){
        cartService.deleteAll(session);
        return "redirect:/users/profile";
    }
}
