package project.model.entities;

import project.model.entities.enums.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="brands")
public class Brand extends BaseEntity {
    @Column(name="name",nullable = false)
    private String name;

    public Brand() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
