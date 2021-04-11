package project.serviceimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.model.entities.UserEntity;
import project.model.entities.UserRoleEntity;
import project.model.entities.enums.UserRole;
import project.model.services.UserServiceModel;
import project.repository.UserRepository;
import project.repository.UserRoleRepository;
import project.service.impl.CloudinaryServiceImpl;
import project.service.impl.ShoeShopUserService;
import project.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    UserEntity user1;


    private UserServiceImpl serviceToTest;
    private CloudinaryServiceImpl cloudinaryService;

    @Mock
    private UserRoleRepository mockRoleRepo;
    @Mock
    private UserRepository mockUserRepo;

    private ShoeShopUserService shoeShopUserService;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init(){
        user1= new UserEntity();
        user1.setUsername("luchob");
        user1.setPassword("xyz");
        user1.setEmail("ads@das.sad");
        user1.setFullname("Ivan ivan");


        UserRoleEntity roleUser = new UserRoleEntity();
        roleUser.setRole(UserRole.USER);
        UserRoleEntity roleAdmin = new UserRoleEntity();
        roleAdmin.setRole(UserRole.ADMIN);

        user1.setRoles(List.of(roleUser, roleAdmin));


        serviceToTest= new UserServiceImpl(mockRoleRepo,mockUserRepo,passwordEncoder,new ModelMapper(),shoeShopUserService, cloudinaryService);

    }


    @Test
    public void testFindByUsername(){
        when(mockUserRepo.findByUsername("sami")).thenReturn(Optional.of(user1));

        UserServiceModel testUser=serviceToTest.findByUsername("sami");

        Assertions.assertEquals(user1.getEmail(),testUser.getEmail());
        Assertions.assertEquals(user1.getFullname(),testUser.getFullname());
        Assertions.assertEquals(user1.getUsername(),testUser.getUsername());
        Assertions.assertEquals(user1.getRoles(),testUser.getRoles());

    }
    @Test
    public void testUsernameExists(){
        when(mockUserRepo.findByUsername("sami")).thenReturn(Optional.of(user1));

        Assertions.assertTrue(this.serviceToTest.userNameExists("sami"));

    }


}
