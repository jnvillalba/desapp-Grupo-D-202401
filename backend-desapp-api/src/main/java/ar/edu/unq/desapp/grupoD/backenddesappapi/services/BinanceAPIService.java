package ar.edu.unq.desapp.grupod.backenddesappapi.services;

import ar.edu.unq.desapp.grupod.backenddesappapi.exceptions.BinancePriceFetchException;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.BinancePriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BinanceAPIService {

    //@Value("${integration.binance.api.url:NONE}")
    private static final String BINANCE_API_URL = "https://api1.binance.com/api/v3/";
    private RestTemplate restTemplate = new RestTemplate();


    @CacheEvict(cacheNames = { "cryptoCache" }, allEntries = true)
    public void clearCache() {
        log.info("Clear Cache cryptoCache");
    }

    @Cacheable(value = "cryptoCache", key = "#symbol")
    public BinancePriceDTO getPriceOfCoinSymbol(String symbol) {
        String url = BINANCE_API_URL + "ticker/price?symbol=" + symbol;
        ResponseEntity<BinancePriceDTO> responseEntity = restTemplate.getForEntity(url, BinancePriceDTO.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new BinancePriceFetchException(symbol, responseEntity.getStatusCode().value());
        }
    }

    public List<BinancePriceDTO> getPricesOfCoins() {
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
        List<BinancePriceDTO> pricesList = new ArrayList<>();
        for (String symbol : symbols) {
            pricesList.add(getPriceOfCoinSymbol(symbol));
        }
        return pricesList;
    }

    public List<BinancePriceDTO> last24HrsPrices(String symbol) {
        long endTime = Instant.now().toEpochMilli();
        long startTime = endTime - (24 * 60 * 60 * 1000);

        String apiUrl = BINANCE_API_URL + "klines?symbol=" + symbol + "&interval=1h&startTime=" + startTime + "&endTime=" + endTime;

        String[][] response = restTemplate.getForObject(apiUrl, String[][].class);

        if (response == null) {
            throw new BinancePriceFetchException(symbol);
        }
        return mapResponseToBinancePriceDTOList(response, symbol);
    }

    private List<BinancePriceDTO> mapResponseToBinancePriceDTOList(String[][] response, String symbol) {
        List<BinancePriceDTO> resultList = new ArrayList<>();

        for (String[] data : response) {
            long timestampInMillis = Long.parseLong(data[6]);
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampInMillis), ZoneId.systemDefault());
            dateTime = dateTime.withMinute(0);

            BinancePriceDTO binancePriceDTO = new BinancePriceDTO(
                    symbol,
                    Float.parseFloat(data[4]),
                    dateTime
            );

            resultList.add(binancePriceDTO);
        }

        return resultList;
    }
}
