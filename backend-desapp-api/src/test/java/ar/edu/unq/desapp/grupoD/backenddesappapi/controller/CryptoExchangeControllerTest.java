package ar.edu.unq.desapp.grupoD.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ExpressIntentionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.IntentionService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.UserService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.webservice.CryptoExchangeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CryptoExchangeControllerTest {
    @Autowired
    private CryptoExchangeController cryptoExchangeController;

    @Autowired
    private MockMvc mvc;

//    @MockBean
//    private IntentionService intentionService;
//
//    @MockBean
//    private UserService userService;

    @Test
    void expressIntention() throws Exception {
        User user = Mockito.mock(User.class);
        Intention intention = Mockito.mock(Intention.class);
        //Mockito.when(userService.getUser(Mockito.any(Long.class))).thenReturn(user);
        //Mockito.when(intentionService.expressIntention(Mockito.any(User.class), Mockito.any(ExpressIntentionDTO.class))).thenReturn(intention);
        ExpressIntentionDTO expressIntentionDTO = new ExpressIntentionDTO();
        expressIntentionDTO.setUserId(1L);
        expressIntentionDTO.setOperationType(OperationType.BUY);
        expressIntentionDTO.setActiveId(1L);
        expressIntentionDTO.setPesosAmount(2);
        mvc.perform(
            post("/api/crypto/intention")
            .content(asJsonString(expressIntentionDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isCreated())
        .andExpect(content().string("Intention expressed successfully"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}