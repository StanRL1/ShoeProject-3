package project.web;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.model.bindings.UserRegistrationBindingModel;
import project.model.bindings.UserUpdateBindingModel;
import project.model.services.UserRegistrationServiceModel;
import project.model.services.UserServiceModel;
import project.model.view.ItemViewModel;
import project.model.view.UserViewModel;
import project.service.CartService;
import project.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CartService cartService;
    @Autowired

    public UserController(ModelMapper modelMapper, UserService userService, CartService cartService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cartService = cartService;
    }


    @ModelAttribute("registrationBindingModel")
    public UserRegistrationBindingModel createBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/login")
    public String login(){


        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public String registerAndLoginUser(
            @Valid UserRegistrationBindingModel registrationBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
        this.userService.seedUsers();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registrationBindingModel", registrationBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registrationBindingModel", bindingResult);

            return "redirect:/users/register";
        }

        if (userService.userNameExists(registrationBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("registrationBindingModel", registrationBindingModel);
            redirectAttributes.addFlashAttribute("userExistsError", true);

            return "redirect:/users/register";
        }

        UserRegistrationServiceModel userServiceModel = modelMapper
                .map(registrationBindingModel, UserRegistrationServiceModel.class);
        List<ItemViewModel> items = new ArrayList<>();

        session.setAttribute("wishlist",items);
        userService.registerAndLoginUser(userServiceModel);

        return "redirect:/home";
    }

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                      String username,
                              RedirectAttributes attributes) {

        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("username", username);

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView, @AuthenticationPrincipal UserDetails principal,HttpSession httpSession){
        UserServiceModel userServiceModel=this.userService.findByUsername(principal.getUsername());
        UserViewModel userViewModel=this.modelMapper.map(userServiceModel,UserViewModel.class);
        modelAndView.addObject("user",userViewModel);
        List<ItemViewModel> wishlist= (List<ItemViewModel>) httpSession.getAttribute("wishlist");
        if(wishlist==null||wishlist.size()==0){
            modelAndView.addObject("exists",false);
        } else{
            modelAndView.addObject("wishlist",(List<ItemViewModel>)httpSession.getAttribute("wishlist"));
            modelAndView.addObject("exists",true);

        }
        modelAndView.setViewName("profile");
        modelAndView.addObject("price",cartService.price(httpSession));
        return modelAndView;
    }

    @GetMapping("/profile/update")
    public ModelAndView profileUpdate(@AuthenticationPrincipal UserDetails principal,ModelAndView modelAndView){


        modelAndView.addObject("user",this.modelMapper.map(this.userService.findByUsername(principal.getUsername()),UserViewModel.class));

        modelAndView.setViewName("profile-update");
        return modelAndView;
    }


    @PatchMapping("/profile/update/{id}")
    public String updateConfirm(@PathVariable Long id, UserUpdateBindingModel userUpdateBindingModel,BindingResult bindingResult,RedirectAttributes attributes,@AuthenticationPrincipal UserDetails principal) throws IOException {

        if(bindingResult.hasErrors()){

            attributes.addFlashAttribute("user", this.modelMapper.map(this.userService.findByUsername(principal.getUsername()),UserViewModel.class));
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userUpdateBindingModel", bindingResult);

            return "redirect:/users/profile/update";
        }

        if(!userUpdateBindingModel.getUsername().equals(principal.getUsername())) {
            if (userService.userNameExists(userUpdateBindingModel.getUsername())) {
                attributes.addFlashAttribute("user", this.modelMapper.map(this.userService.findByUsername(principal.getUsername()), UserViewModel.class));
                attributes.addFlashAttribute("userExistsError", true);

                return "redirect:/users/profile/update";
            }
        }
        UserRegistrationServiceModel userServiceMode=this.modelMapper.map(userUpdateBindingModel,UserRegistrationServiceModel.class);

        this.userService.updateUser(userServiceMode,id);

        return "redirect:/home";
    }

}
