package project.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.model.view.ItemViewModel;
import project.service.FrontPageService;

@Controller

public class HomeController {

    private final FrontPageService frontPageService;
    private final ModelMapper modelMapper;
    @Autowired

    public HomeController(FrontPageService frontPageService, ModelMapper modelMapper) {
        this.frontPageService = frontPageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String index() {


        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("firstItem",this.modelMapper.map(frontPageService.firstImage(), ItemViewModel.class));
        model.addAttribute("secondItem",this.modelMapper.map(frontPageService.secondImage(), ItemViewModel.class));
        model.addAttribute("thirdItem",this.modelMapper.map(frontPageService.thirdImage(), ItemViewModel.class));
        return "home";
    }


}
