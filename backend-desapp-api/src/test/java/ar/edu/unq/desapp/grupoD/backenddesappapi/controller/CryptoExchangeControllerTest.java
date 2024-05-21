package ar.edu.unq.desapp.grupoD.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ExpressIntentionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ProcessTransactionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.BinanceAPIService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.IntentionService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.TransactionService;
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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType.BUY;
import static ar.edu.unq.desapp.grupoD.backenddesappapi.model.OperationType.SELL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @SpyBean
    private UserService userService;

    @MockBean
    private TransactionService transactionService;

    @SpyBean
    private IntentionService intentionService;

    @Test
    void testGetCryptoCurrencyValue() throws Exception {
        BinancePriceDTO priceDTO = new BinancePriceDTO("BTC", 50000.0F, LocalDateTime.now());
        Mockito.when(binanceAPIService.getPriceOfCoinSymbol("BTC")).thenReturn(priceDTO);

        mvc.perform(get("/api/crypto/crypto/BTC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("BTC"))
                .andExpect(jsonPath("$.price").value(50000.0));
    }

    @Test
    void testGetPricesOfCoins() throws Exception {
        BinancePriceDTO price1 = new BinancePriceDTO("BTC", 50000.0F, LocalDateTime.now());
        BinancePriceDTO price2 = new BinancePriceDTO("ETH", 3000.0F, LocalDateTime.now());
        List<BinancePriceDTO> prices = Arrays.asList(price1, price2);

        Mockito.when(binanceAPIService.getPricesOfCoins()).thenReturn(prices);

        mvc.perform(get("/api/crypto/crypto/prices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("BTC"))
                .andExpect(jsonPath("$[0].price").value(50000.0))
                .andExpect(jsonPath("$[1].symbol").value("ETH"))
                .andExpect(jsonPath("$[1].price").value(3000.0));
    }

    @Test
    void testProcessTransaction() throws Exception {
        ProcessTransactionDTO trx = new ProcessTransactionDTO();

        Operation operation = new Operation();
        operation.setStatus(Operation.TransactionStatus.CONFIRMED);

        Mockito.when(transactionService.processTransaction(Mockito.any(ProcessTransactionDTO.class)))
                .thenReturn(operation);

        mvc.perform(post("/api/crypto/operation/processTransaction")
                        .content(asJsonString(trx))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Operation.TransactionStatus.CONFIRMED.toString()));
    }

    @Test
    void expressIntention() throws Exception {
        ExpressIntentionDTO expressIntentionDTO = new ExpressIntentionDTO();
        expressIntentionDTO.setUserId(1L);
        expressIntentionDTO.setOperationType(BUY);
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

    @Test
    void testGetAllIntentions() throws Exception {
        Intention intention1 = new Intention();
        intention1.setIntentionId(1L);
        intention1.setOperationType(OperationType.BUY);

        Intention intention2 = new Intention();
        intention2.setIntentionId(2L);
        intention2.setOperationType(OperationType.SELL);

        List<Intention> intentions = Arrays.asList(intention1, intention2);
        Mockito.when(intentionService.getAllIntentions()).thenReturn(intentions);

        mvc.perform(get("/api/crypto/intentions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].intentionId").value(1L))
                .andExpect(jsonPath("$[0].operationType").value("BUY"))
                .andExpect(jsonPath("$[1].intentionId").value(2L))
                .andExpect(jsonPath("$[1].operationType").value("SELL"));
    }
}
