package project.model.services;

import project.model.entities.Item;
import project.model.entities.UserRoleEntity;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UserServiceModel {
    @NotNull
    private String username;
    @NotNull
    private String email;

    @NotNull
    private String fullname;
    @NotNull
    private List<UserRoleEntity> roles;
    @NotNull
    private String img;


    public UserServiceModel() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
