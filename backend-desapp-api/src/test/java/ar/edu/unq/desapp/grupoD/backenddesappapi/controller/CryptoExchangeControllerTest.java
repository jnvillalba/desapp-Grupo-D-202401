package ar.edu.unq.desapp.grupoD.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ExpressIntentionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.BinanceAPIService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.webservice.CryptoExchangeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
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

    @MockBean
    private BinanceAPIService binanceAPIService;

    @Test
    void expressIntention() throws Exception {
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
        .andExpect(content().string("Intention of BUY expressed successfully"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
