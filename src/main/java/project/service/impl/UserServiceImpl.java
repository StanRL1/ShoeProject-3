package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.exeptions.ObjectNotFoundException;
import project.model.entities.UserEntity;
import project.model.entities.UserRoleEntity;
import project.model.entities.enums.UserRole;
import project.model.services.UserRegistrationServiceModel;
import project.model.services.UserServiceModel;
import project.repository.UserRepository;
import project.repository.UserRoleRepository;
import project.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ShoeShopUserService shoeShopUserService;
    @Autowired

    public UserServiceImpl(UserRoleRepository userRoleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper,
                           ShoeShopUserService shoeShopUserService) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.shoeShopUserService = shoeShopUserService;

    }
    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel serviceModel) {
        UserEntity newUser = modelMapper.map(serviceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(serviceModel.getPassword()));

        UserRoleEntity userRole = userRoleRepository.
                findByRole(UserRole.USER).orElseThrow(ObjectNotFoundException::new);

        newUser.addRole(userRole);

        newUser = userRepository.save(newUser);

        UserDetails principal = shoeShopUserService.loadUserByUsername(newUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    @Async
    public void seedUsers() {

        if (this.userRepository.count() == 0) {

        UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
        UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

        userRoleRepository.saveAll(List.of(adminRole, userRole));

        UserEntity admin = new UserEntity().setUsername("admin").setFullname("Admin Adminov").setPassword(passwordEncoder.encode("topsecret"));
        UserEntity user = new UserEntity().setUsername("user").setFullname("Bai Ivan").setPassword(passwordEncoder.encode("topsecret"));
        admin.setEmail("admin@admin.com");
        user.setEmail("user@user.com");
        admin.setRoles(List.of(adminRole, userRole));
        user.setRoles(List.of(userRole));

        userRepository.saveAll(List.of(admin, user));
    }

    }

    @Override
    public UserServiceModel findByUsername(String username) {
        UserServiceModel userServiceModel= this.modelMapper.map(this.userRepository.findByUsername(username).orElseThrow(ObjectNotFoundException::new),UserServiceModel.class);

        return userServiceModel;
    }


    @Override
    public boolean userNameExists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }
}
