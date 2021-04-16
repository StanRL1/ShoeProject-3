package project.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.model.entities.Comment;
import project.model.entities.Item;
import project.model.entities.UserEntity;
import project.model.entities.UserRoleEntity;
import project.model.entities.enums.Gender;
import project.model.entities.enums.UserRole;
import project.repository.CommentRepository;
import project.repository.UserRepository;
import project.repository.UserRoleRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CommentControllerTest {

    UserEntity admin;
    Item item1;
    private static final String COMMENT_CONTROLLER_PREFIX="/comments";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    public void init(){
        UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
        UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);
        this.userRoleRepository.saveAndFlush(adminRole);
        this.userRoleRepository.saveAndFlush(userRole);


        admin = new UserEntity();
        admin.setUsername("admin");
        admin.setFullname("Admin Adminov");
        admin.setPassword("topsecret");
        admin.setEmail("admin@admin.com");
        admin.setRoles(List.of(adminRole, userRole));
        admin.setImg("https://img.icons8.com/bubbles/100/000000/user.png");
        this.userRepository.saveAndFlush(admin);

        item1 = new Item();
        item1.setName("item1");
        item1.setAddedBy("pesho");
        item1.setDescription("11111111111");
        item1.setGender(Gender.FEMALE);
        item1.setImgUrl("1111111111111");
        item1.setPrice(BigDecimal.TEN);
        item1.setId(1);

        Comment comment1=new Comment();
        comment1.setWriter(admin);
        comment1.setContent("aspdmaspkd");
        comment1.setLocalDate(LocalDate.now());
        comment1.setItem(item1);
        comment1.setId(1);

        this.commentRepository.saveAndFlush(comment1);

    }

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testDeleteMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                COMMENT_CONTROLLER_PREFIX + "/delete/{id}", 1L
        )).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/comments/item/1"));

        Assertions.assertEquals(0,this.commentRepository.count());
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testGetComments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                COMMENT_CONTROLLER_PREFIX + "/item/{id}", 1L
        )).
                andExpect(status().isOk()).
                andExpect(view().name("comments")).
                andExpect(model().attributeExists("comments")).
                andExpect(model().attributeExists("username"));
    }
//    @Test
//    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
//    void testAddCommentMethod() throws Exception {
//        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(
//                COMMENT_CONTROLLER_PREFIX + "/item"
//                ).param("id","1").
//                        param("writer", String.valueOf(admin)).
//                        param("content", "asdasdasd").
//                        param("localDate", String.valueOf(LocalDate.now())).param("item",item1.toString()).with(csrf())
//
//        );
//        perform.andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/comments/item/1"));
//
//        Assertions.assertEquals(4, this.commentRepository.count());
//    }
    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testAddCommentMethodFail() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(
                COMMENT_CONTROLLER_PREFIX + "/item"
                ).param("id","1").
                        param("writer", "admin").
                        param("localDate", String.valueOf(LocalDate.now())).param("item",item1.toString()).with(csrf())

        );
        perform.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/comments/item/1"));

        Assertions.assertEquals(1, this.commentRepository.count());
    }
}
