package project.model.entities;

import org.springframework.security.core.userdetails.User;
import project.model.entities.enums.BaseEntity;
import project.model.entities.enums.Gender;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name="items")
public class Item extends BaseEntity {
    @Column(name="name",nullable = false)
    @Size(min=3,max=20)
    private String name;
    @Column(name="description",nullable = false)
    @Size(min=5,max=50)
    private String description;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="price",nullable = false)
    @DecimalMin("0")
    private BigDecimal price;
    @Column(name="added_by",nullable = false)
    private String addedBy;
    @Column(name="image_url",nullable = false)
    private String imgUrl;

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
