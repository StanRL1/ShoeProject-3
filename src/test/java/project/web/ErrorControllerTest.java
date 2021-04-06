package project.web;

import org.junit.jupiter.api.Assertions;
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
public class ErrorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "pesho", roles = {"USER", "ADMIN"})
    void testError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                "/error"
        )).
                andExpect(status().is2xxSuccessful()).
                andExpect(view().name("error"));
    }

}
