package project.model.bindings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentBindingModel {
    @NotNull
    @Size(min=1,max=150)
    private String content;


    public CommentBindingModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
