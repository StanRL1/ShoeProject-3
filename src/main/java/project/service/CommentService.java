package project.service;

import project.model.services.CommentServiceModel;

import java.util.List;

public interface CommentService {
    List<CommentServiceModel> findCommentsById(Long id);

    void addComment(CommentServiceModel commentServiceModel);

    void deleteById(Long id);

    CommentServiceModel findById(Long id);
}
