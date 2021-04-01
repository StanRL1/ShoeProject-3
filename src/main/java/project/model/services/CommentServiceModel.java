package project.model.services;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentServiceModel {
    private Long id;
    @NotNull
    @Size(min=1,max=150)
    private String content;
    private String writer;
    @NotNull
    private Long itemId;

    public CommentServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
