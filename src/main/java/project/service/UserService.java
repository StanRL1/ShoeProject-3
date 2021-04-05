package project.service;

import project.model.services.UserRegistrationServiceModel;
import project.model.services.UserServiceModel;

public interface UserService {
    boolean userNameExists(String username);

    void registerAndLoginUser(UserRegistrationServiceModel userServiceModel);

    void seedUsers();

    UserServiceModel findByUsername(String username);
}
