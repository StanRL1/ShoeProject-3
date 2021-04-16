package project.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.view.ItemViewModel;
import project.model.view.UserViewModel;
import project.service.ItemService;
import project.service.LogService;
import project.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/statistics")
public class StatController {
    private final LogService logService;
    private final ItemService itemService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public StatController(LogService logService, ItemService itemService, UserService userService, ModelMapper modelMapper) {
        this.logService = logService;
        this.itemService = itemService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String stats(Model model){
        model.addAttribute("logs",this.logService.findAllLogs());

        return "statistics";
    }

    @GetMapping("/customers")
    public String statsCustomers(Model model){
        List<UserViewModel> userViewModelList=this.userService.findAll().stream()
                .map(user->this.modelMapper.map(user,UserViewModel.class)).collect(Collectors.toList());

      model.addAttribute("users",userViewModelList);

        return "customers";
    }

    @GetMapping("/products")
    public String statsItems(Model model){
        List<ItemViewModel> itemViewModels=this.itemService.findAllItems().stream()
                .map(item->this.modelMapper.map(item,ItemViewModel.class)).collect(Collectors.toList());

        model.addAttribute("items",itemViewModels);

        return "products";
    }

}
