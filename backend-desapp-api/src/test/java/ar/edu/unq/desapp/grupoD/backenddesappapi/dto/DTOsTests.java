package ar.edu.unq.desapp.grupoD.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ActiveDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DTOsTests {

    @Test
    public void testToActiveDTO() {
        CryptoActive cryptoActive = new CryptoActive();
        cryptoActive.setSymbol("BTC");
        cryptoActive.setAmount(1.0);
        cryptoActive.setPrice(50000f);

        ActiveDTO activeDTO = ActiveDTO.toActiveDTO(cryptoActive, 1000f);

        assertNotNull(activeDTO);
        assertEquals("BTC", activeDTO.getCrypto());
        assertEquals(1.0, activeDTO.getNominalCryptoAmount());
        assertEquals(50000f, activeDTO.getCurrentCryptoPrice());
        assertEquals(50000f * 1000f, activeDTO.getPriceInPesosARG());
    }
}
