package ar.edu.unq.desapp.grupod.backenddesappapi.controller;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.cache.CacheManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void clearCache() {
        cacheManager.getCache("cryptoCache").clear();
    }


    @Test
    void registerUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setDirection("123 Main St");
        userDTO.setPassword("Password@123");
        userDTO.setCvuMercadoPago("1234567890123456789012");
        userDTO.setWalletCrypto("12345678");

        User user = userDTO.toModel();

        mvc.perform(
                        post("/api/auth/register")
                                .content(asJsonString(userDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userDTO.getEmail()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

