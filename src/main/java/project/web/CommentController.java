package project.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.model.bindings.CommentBindingModel;
import project.model.services.CommentServiceModel;
import project.model.view.CommentViewModel;
import project.service.CommentService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;
    @Autowired

    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("item/{id}")
    public ModelAndView comments(@PathVariable("id") Long id,ModelAndView modelAndView,@AuthenticationPrincipal UserDetails principal){
        List<CommentViewModel> commentViewModelList=this.commentService.findCommentsById(id).stream()
                .map(comment->{
                    CommentViewModel commentViewModel=this.modelMapper.map(comment,CommentViewModel.class);
                    return commentViewModel;
                }).collect(Collectors.toList());
        modelAndView.addObject("comments",commentViewModelList);
        modelAndView.setViewName("comments");
        modelAndView.addObject("username",principal.getUsername());
        return modelAndView;
    }
    @PostMapping("/item")
    public String addComment(@Valid @ModelAttribute("commentBindingModel")CommentBindingModel commentBindingModel, BindingResult bindingResult,
                             @RequestParam("id") Long id, @AuthenticationPrincipal UserDetails principal)

    {
        if(bindingResult.hasErrors()){
            return ("redirect:/comments/item/"+id);
        }
        CommentServiceModel commentServiceModel=this.modelMapper.map(commentBindingModel,CommentServiceModel.class);
        commentServiceModel.setItemId(id);
        commentServiceModel.setWriter(principal.getUsername());
        commentServiceModel.setLocalDate(LocalDate.now());
        System.out.println();
        this.commentService.addComment(commentServiceModel);

        return ("redirect:/comments/item/"+id);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        CommentServiceModel commentServiceModel=this.commentService.findById(id);
        this.commentService.deleteById(id);

        return ("redirect:/comments/item/"+commentServiceModel.getItemId());
    }
}
