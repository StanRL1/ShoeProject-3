package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.exeptions.ObjectNotFoundException;
import project.model.entities.Comment;
import project.model.entities.Item;
import project.model.entities.UserEntity;
import project.model.services.CommentServiceModel;
import project.repository.CommentRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;
import project.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, ItemRepository itemRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }



    @Override
    public List<CommentServiceModel> findCommentsById(Long id) {
        List<CommentServiceModel> commentServiceModels=this.commentRepository.findAllByItemId(id).stream().
                map(comment->{
                    CommentServiceModel commentServiceModel=this.modelMapper.map(comment,CommentServiceModel.class);
                    commentServiceModel.setItemId(id);
                    commentServiceModel.setWriter(comment.getWriter().getUsername());
                    return commentServiceModel;
                }).collect(Collectors.toList());
        return commentServiceModels;
    }

    @Override
    public void addComment(CommentServiceModel commentServiceModel) {
        Item item =this.itemRepository.findById(commentServiceModel.getItemId()).orElseThrow(ObjectNotFoundException::new);;
        UserEntity user=this.userRepository.findByUsername(commentServiceModel.getWriter()).orElseThrow(ObjectNotFoundException::new);
        Comment comment=this.modelMapper.map(commentServiceModel,Comment.class);
        comment.setItem(item);
        comment.setWriter(user);

        this.commentRepository.saveAndFlush(comment);
    }

    @Override
    public void deleteById(Long id) {
        this.commentRepository.deleteById(id);
    }

    @Override
    public CommentServiceModel findById(Long id) {

        Comment comment=this.commentRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        CommentServiceModel commentServiceModel=this.modelMapper.map(comment,CommentServiceModel.class);
        commentServiceModel.setItemId(comment.getItem().getId());


        return commentServiceModel;
    }

    @Override
    public void deleteCommentsByItemId(Long id) {
        if(this.commentRepository.findAllByItemId(id).size()>0)
            System.out.println();
            List<Comment>comments =this.commentRepository.findAllByItemId(id);
        for (Comment c:comments) {
            this.commentRepository.delete(c);
        }
    }
}
