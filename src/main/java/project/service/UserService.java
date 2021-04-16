package project.service;

import org.springframework.security.core.userdetails.User;
import project.model.entities.UserEntity;
import project.model.services.UserRegistrationServiceModel;
import project.model.services.UserServiceModel;

import java.io.IOException;
import java.util.List;

public interface UserService {
    boolean userNameExists(String username);

    void registerAndLoginUser(UserRegistrationServiceModel userServiceModel) throws IOException;

    void seedUsers();

    UserServiceModel findByUsername(String username);

    List<UserServiceModel> findAll();

    UserServiceModel findById(Long id);

    void updateUser(UserRegistrationServiceModel map, Long id) throws IOException;
}
