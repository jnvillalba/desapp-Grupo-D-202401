package ar.edu.unq.desapp.grupoD.backenddesappapi.service.binance;

import ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions.BinancePriceFetchException;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CriptoActive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinanceAPIService {
    /**
     * TODO: pasar a env
     *
     * @Value("${integration.binance.api.url}") private String binanceApiURL;
     */
    private static final String BINANCE_API_URL = "https://api1.binance.com/api/v3/";
    private final RestTemplate restTemplate;

    @Autowired
    public BinanceAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CriptoActive getPriceOfCoinSymbol(String symbol) {
        String url = BINANCE_API_URL + "ticker/price?symbol=" + symbol;
        ResponseEntity<CriptoActive> responseEntity = restTemplate.getForEntity(url, CriptoActive.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new BinancePriceFetchException(symbol, responseEntity.getStatusCode().value());
        }
    }

    public List<CriptoActive> getPricesOfCoins() {
        String[] symbols = {
                "ALICEUSDT",
                "MATICUSDT",
                "AXSUSDT",
                "AAVEUSDT",
                "ATOMUSDT",
                "NEOUSDT",
                "DOTUSDT",
                "ETHUSDT",
                "CAKEUSDT",
                "BTCUSDT",
                "BNBUSDT",
                "ADAUSDT",
                "TRXUSDT",
                "AUDIOUSDT"
        };
        List<CriptoActive> pricesList = new ArrayList<>();
        for (String symbol : symbols) {
            pricesList.add(getPriceOfCoinSymbol(symbol));
        }
        return pricesList;
    }

    public List<CriptoActive> last24HrsPrices(String symbol) {
        long endTime = Instant.now().toEpochMilli();
        long startTime = endTime - (24 * 60 * 60 * 1000);

        String apiUrl = BINANCE_API_URL + "klines?symbol=" + symbol + "&interval=1h&startTime=" + startTime + "&endTime=" + endTime;

        String[][] response = restTemplate.getForObject(apiUrl, String[][].class);

        return mapResponseToBinancePriceDTOList(response, symbol);
    }

    private List<CriptoActive> mapResponseToBinancePriceDTOList(String[][] response, String symbol) {
        List<CriptoActive> resultList = new ArrayList<>();

        for (String[] data : response) {
            long timestampInMillis = Long.parseLong(data[6]);
            // TODO revisar el calculo de hora
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampInMillis), ZoneId.systemDefault());
            dateTime = dateTime.withMinute(0);

            CriptoActive binancePriceDTO = new CriptoActive(
                    symbol,
                    Float.parseFloat(data[4]),
                    dateTime
            );

            resultList.add(binancePriceDTO);
        }

        return resultList;
    }

}
