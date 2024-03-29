package ar.edu.unq.desapp.grupoD.backenddesappapi.service.binance;

import ar.edu.unq.desapp.grupoD.backenddesappapi.persistence.binance.DTO.BinancePriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BinanceAPIService {
    // TODO: pasar a env
//    @Value("${integration.binance.api.url}")
//    private String binanceApiURL;
    private static final String BINANCE_API_URL = "https://api1.binance.com/api/v3/ticker/price?symbol=";
    private final RestTemplate restTemplate;

    @Autowired
    public BinanceAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BinancePriceDTO getPriceOfCoinSymbol(String symbol) {
        String url = BINANCE_API_URL + symbol;
        ResponseEntity<BinancePriceDTO> responseEntity = restTemplate.getForEntity(url, BinancePriceDTO.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            //TODO: crear una exception especifica
            throw new RuntimeException("Failed to get price for symbol " + symbol + " from Binance API. Status code: " + responseEntity.getStatusCodeValue());

        }
    }
}
