package project.model.entities;
import project.model.entities.enums.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="comments")
public class Comment extends BaseEntity {
    @Column(name="content",nullable = false)
    @Size(min=1,max=150)
    private String content;
    @ManyToOne
    private UserEntity writer;
    @ManyToOne
    private Item item;
    @FutureOrPresent
    private LocalDate localDate;

    public Comment() {
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getWriter() {
        return writer;
    }

    public void setWriter(UserEntity writer) {
        this.writer = writer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
