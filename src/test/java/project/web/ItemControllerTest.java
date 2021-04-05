package project.web;

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
    void testShouldReturnValidStatusViewNameAndModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                ITEM_CONTROLLER_PREFIX + "/details", 1
        )).
                andExpect(status().isOk()).
                andExpect(view().name("details-item")).
                andExpect(model().attributeExists("item")).
                andExpect(model().attributeExists("username"));
    }

    public void init(){
        Item item1 = new Item();
        item1.setName("item1");
        item1.setAddedBy("addedby1");
        item1.setDescription("11111111111");
        item1.setGender(Gender.FEMALE);
        item1.setImgUrl("1111111111111");
        item1.setPrice(BigDecimal.TEN);
        item1.setId(1);
        Item item2 = new Item();
        item2.setName("item2");
        item2.setAddedBy("addedby2");
        item2.setDescription("22222222222");
        item2.setGender(Gender.MALE);
        item2.setImgUrl("222222222222");
        item2.setPrice(BigDecimal.TEN);
        item2.setId(2);
        Item item3 = new Item();
        item2.setName("item2");
        item2.setAddedBy("addedby2");
        item2.setDescription("22222222222");
        item2.setGender(Gender.MALE);
        item2.setImgUrl("222222222222");
        item2.setPrice(BigDecimal.TEN);
        item2.setId(2);


    }
}
