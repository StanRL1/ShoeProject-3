package project.model.entities;
import project.model.entities.enums.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="comments")
public class Comment extends BaseEntity {
    @Column(name="content",nullable = false)
    @Size(min=1,max=150)
    private String content;
    @Column(name="writer",nullable = false)
    private String writer;
    @ManyToOne
    private Item item;

    public Comment() {
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
