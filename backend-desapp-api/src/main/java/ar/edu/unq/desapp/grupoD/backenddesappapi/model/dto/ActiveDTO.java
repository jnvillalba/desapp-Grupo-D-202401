package ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CryptoActive;
import lombok.Data;

@Data
public class ActiveDTO {
    private String crypto;
    private Double nominalCryptoAmount;
    private Float currentCryptoPrice;
    private Float priceInPesosARG;

    public ActiveDTO toActiveDTO(CryptoActive cryptoActive, Float dolarBlue ) {
        ActiveDTO active = new ActiveDTO();
        active.setCrypto(cryptoActive.getSymbol());
        active.setNominalCryptoAmount(cryptoActive.getAmount());
        Float price = cryptoActive.getPrice();
        active.setCurrentCryptoPrice(price);
        active.setPriceInPesosARG(price*dolarBlue);
        return active;
    }
}
