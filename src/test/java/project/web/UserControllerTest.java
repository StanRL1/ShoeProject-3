package project.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.model.entities.UserEntity;
import project.model.entities.UserRoleEntity;
import project.model.entities.enums.Gender;
import project.model.entities.enums.UserRole;
import project.repository.UserRepository;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {
    private static final String USER_CONTROLLER_PREFIX="/users";
    UserEntity user1;
    UserRoleEntity userRoleEntity1,userRoleEntity2;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        UserRoleEntity userRoleEntity1=new UserRoleEntity();
        userRoleEntity1.setRole(UserRole.ADMIN);
        UserRoleEntity userRoleEntity2=new UserRoleEntity();
        userRoleEntity2.setRole(UserRole.USER);

        user1= new UserEntity();
        user1.setFullname("Ivan ivanov");
        user1.setEmail("asdas@avbv.bgbf");
        user1.setUsername("user");
        user1.setPassword("12345");
        user1.setRoles(List.of(userRoleEntity1,userRoleEntity2));
        user1.setId(1);

    }

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                USER_CONTROLLER_PREFIX + "/login"
        )).
                andExpect(status().isOk()). andExpect(view().name("login"));
    }
    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                USER_CONTROLLER_PREFIX + "/register"
        )).
                andExpect(status().isOk()). andExpect(view().name("register"));
    }
//    @Test
//    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
//    void testProfile() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get(
//                USER_CONTROLLER_PREFIX + "/profile"
//        )).
//                andExpect(status().isOk()).
//                andExpect(view().name("profile")).
//                andExpect(model().attributeExists("user"));
//    }

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testLoginError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                USER_CONTROLLER_PREFIX + "/login-error"
                ).with(csrf())
        ).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testRegisterPostFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                USER_CONTROLLER_PREFIX + "/register"
                ).param("username","admin").param("password","123456").
                param("fullname","IVAN GORCHETO").param("email","email@turbo.com")
                .with(csrf())
        ).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/users/register"));

        Assertions.assertEquals(2, this.userRepository.count());

    }

//    @Test
//    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
//    void testRegisterPost() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post(
//                USER_CONTROLLER_PREFIX + "/register"
//                ).param("username","admin").param("password","123456").
//                        param("fullname","IVAN GORCHETO").param("email","email@turbo.com").
//                        param("roles", String.valueOf(List.of(userRoleEntity1)))
//                        .with(csrf())
//
//        ).andExpect(status().is3xxRedirection());
//        System.out.println();
//        Assertions.assertEquals(3, this.userRepository.count());
//
//    }





}
