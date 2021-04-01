package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.entities.Comment;
import project.model.entities.Item;
import project.model.services.CommentServiceModel;
import project.repository.CommentRepository;
import project.repository.ItemRepository;
import project.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, ItemRepository itemRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<CommentServiceModel> findCommentsById(Long id) {
        List<CommentServiceModel> commentServiceModels=this.commentRepository.findAllByItemId(id).stream().
                map(comment->{
                    CommentServiceModel commentServiceModel=this.modelMapper.map(comment,CommentServiceModel.class);
                    commentServiceModel.setItemId(id);
                    return commentServiceModel;
                }).collect(Collectors.toList());
        return commentServiceModels;
    }

    @Override
    public void addComment(CommentServiceModel commentServiceModel) {
        Item item =this.itemRepository.findById(commentServiceModel.getItemId()).orElse(null);
        Comment comment=this.modelMapper.map(commentServiceModel,Comment.class);
        comment.setItem(item);

        this.commentRepository.saveAndFlush(comment);
    }

    @Override
    public void deleteById(Long id) {
        this.commentRepository.deleteById(id);
    }

    @Override
    public CommentServiceModel findById(Long id) {
        Comment comment=this.commentRepository.findById(id).orElse(null);
        CommentServiceModel commentServiceModel=this.modelMapper.map(comment,CommentServiceModel.class);
        commentServiceModel.setItemId(comment.getItem().getId());

        return commentServiceModel;
    }
}
