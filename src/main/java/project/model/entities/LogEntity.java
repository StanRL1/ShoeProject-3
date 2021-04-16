package project.model.entities;

import project.model.entities.enums.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="logs")
public class LogEntity extends BaseEntity {
    @Column(name="username",nullable = false)
    private String username;
    @ManyToOne
    private Item item;
    @Column(name="action",nullable = false)
    private String action;
    @Column(name="date_time",nullable = false)
    private LocalDateTime dateTime;

    public LogEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
