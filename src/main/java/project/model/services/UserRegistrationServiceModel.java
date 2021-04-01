package project.model.services;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegistrationServiceModel {
    @NotEmpty
    @Size(min = 3)
    private String username;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 3)
    private String fullname;
    @NotEmpty
    @Size(min = 5, max = 20)
    private String password;


    public String getUsername() {
        return username;
    }

    public UserRegistrationServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullname() {
        return fullname;
    }

    public UserRegistrationServiceModel setFullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
