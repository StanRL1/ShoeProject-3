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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.model.bindings.UserRegistrationBindingModel;
import project.model.services.UserRegistrationServiceModel;
import project.model.services.UserServiceModel;
import project.model.view.UserViewModel;
import project.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final ModelMapper modelMapper;
    private final UserService userService;
    @Autowired

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @ModelAttribute("registrationBindingModel")
    public UserRegistrationBindingModel createBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/login")
    public String login(){
        LOGGER.info("LOG1");
        LOGGER.info("LOG2");
        LOGGER.info("LOG3");
        LOGGER.info("LOG4");

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAndLoginUser(
            @Valid UserRegistrationBindingModel registrationBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

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
    public ModelAndView profile(ModelAndView modelAndView, @AuthenticationPrincipal UserDetails principal){
        UserServiceModel userServiceModel=this.userService.findByUsername(principal.getUsername());
        UserViewModel userViewModel=this.modelMapper.map(userServiceModel,UserViewModel.class);
        modelAndView.addObject("user",userViewModel);
        modelAndView.setViewName("profile");
        return modelAndView;
    }

}
