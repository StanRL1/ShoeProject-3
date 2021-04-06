package project.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                 "/"
        )).
                andExpect(status().isOk()).
        andExpect(view().name("index"));
    }
    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/home"
        )).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("firstItem")).
                andExpect(model().attributeExists("secondItem")).
                andExpect(model().attributeExists("thirdItem")).
                andExpect(view().name("home"));
    }

}
