package project.service;

import project.model.services.UserRegistrationServiceModel;
import project.model.services.UserServiceModel;

import java.io.IOException;

public interface UserService {
    boolean userNameExists(String username);

    void registerAndLoginUser(UserRegistrationServiceModel userServiceModel) throws IOException;

    void seedUsers();

    UserServiceModel findByUsername(String username);
}
