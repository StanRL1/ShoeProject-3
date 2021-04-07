package project.model.view;

import project.model.entities.Brand;
import project.model.entities.enums.Gender;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ItemViewModel {
    @NotNull
    private Long id;
    @NotNull
    @Size(min=3,max=20)
    private String imgUrl;
    @NotNull
    @Size(min=3,max=20)
    private String name;
    @DecimalMin("0")
    private BigDecimal price;
    @NotNull
    private Gender gender;
    @NotNull
    @Size(min=3,max=150)
    private String description;
    @NotNull
    private String addedBy;
    @NotNull
    private Brand brand;

    public ItemViewModel() {
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
