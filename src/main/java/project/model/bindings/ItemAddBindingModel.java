package project.model.bindings;

import project.model.entities.enums.Gender;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ItemAddBindingModel {
    @NotNull
    @Size(min=3,max=20)
    private String name;
    @NotNull
    private String imgUrl;
    @NotNull
    @Size(min=3,max=150)
    private String description;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @DecimalMin("0")
    private BigDecimal price;
    private String addedBy;

    public ItemAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getUsername() {
        return addedBy;
    }

    public void setUsername(String addedBy) {
        this.addedBy = addedBy;
    }
}
