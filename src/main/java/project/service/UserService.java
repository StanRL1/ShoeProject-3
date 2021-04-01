package project.service;

import project.model.services.UserRegistrationServiceModel;

public interface UserService {
    boolean userNameExists(String username);

    void registerAndLoginUser(UserRegistrationServiceModel userServiceModel);

    void seedUsers();
}
