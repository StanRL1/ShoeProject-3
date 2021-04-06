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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.model.entities.Item;
import project.model.entities.enums.Gender;
import project.repository.ItemRepository;
import project.repository.UserRepository;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ItemControllerTest {

    private static final String ITEM_CONTROLLER_PREFIX="/items";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        this.init();
    }
    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testShouldReturnValidStatusViewNameAndModelFrom1stDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                ITEM_CONTROLLER_PREFIX + "/details/{id}", 1L
        )).
                andExpect(status().isOk()).
                andExpect(view().name("details-item")).
                andExpect(model().attributeExists("item")).
                andExpect(model().attributeExists("username"));
    }
    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testShouldReturnValidStatusViewNameAndModelFrom2ndDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                ITEM_CONTROLLER_PREFIX + "/details"
        ).param("id","2")).
                andExpect(status().isOk()).
                andExpect(view().name("details-item")).
                andExpect(model().attributeExists("item")).
                andExpect(model().attributeExists("username"));
    }

//    @Test
//    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
//    void testDeleteMethod() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get(
//                ITEM_CONTROLLER_PREFIX + "/delete/{id}", 1
//        )).
//                andExpect(status().is3xxRedirection()).
//                andExpect(redirectedUrl("/items/my-items"));
//    }
    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testMyItemsLoad() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                ITEM_CONTROLLER_PREFIX + "/my-items"
        )).
                andExpect(status().isOk()).
                andExpect(view().name("my-items")).
                andExpect(model().attributeExists("items")).
                andExpect(model().attributeExists("count"));
    }
    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testAllItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                ITEM_CONTROLLER_PREFIX + "/all"
        )).
                andExpect(status().isOk()).
                andExpect(view().name("all-items")).
                andExpect(model().attributeExists("items")).
                andExpect(model().attributeExists("count"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testAddItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                ITEM_CONTROLLER_PREFIX + "/add"
        )).
                andExpect(status().isOk()).
                andExpect(view().name("add-item"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testAddItemsPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                ITEM_CONTROLLER_PREFIX + "/add"
        ).param("name","testItem").
                param("addedBy","pesho").
                param("description","testItemAdd").
                param("gender",Gender.FEMALE.name()).param("imgUrl","haoshakjls").
                param("price","155").with(csrf())
        ).
                andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/home"));
        Assertions.assertEquals(4,this.itemRepository.count());
    }
    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testAddItemsPostFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                ITEM_CONTROLLER_PREFIX + "/add"
                ).param("name","testItem").
                        param("addedBy","pesho").
                        param("gender",Gender.FEMALE.name()).param("imgUrl","haoshakjls").
                        param("price","155").with(csrf())
        ).
                andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/items/add"));
        Assertions.assertEquals(3,this.itemRepository.count());
    }


    @BeforeEach
    public void init(){
        Item item1 = new Item();
        item1.setName("item1");
        item1.setAddedBy("pesho");
        item1.setDescription("11111111111");
        item1.setGender(Gender.FEMALE);
        item1.setImgUrl("1111111111111");
        item1.setPrice(BigDecimal.TEN);
        item1.setId(1);
        Item item2 = new Item();
        item2.setName("item2");
        item2.setAddedBy("pesho");
        item2.setDescription("22222222222");
        item2.setGender(Gender.MALE);
        item2.setImgUrl("222222222222");
        item2.setPrice(BigDecimal.TEN);
        item2.setId(2);
        Item item3 = new Item();
        item2.setName("item2");
        item2.setAddedBy("pesho");
        item2.setDescription("22222222222");
        item2.setGender(Gender.MALE);
        item2.setImgUrl("222222222222");
        item2.setPrice(BigDecimal.TEN);
        item2.setId(2);



    }
}
