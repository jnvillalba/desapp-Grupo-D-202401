package ar.edu.unq.desapp.grupoD.backenddesappapi.services;

import ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions.BinancePriceFetchException;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ProcessTransactionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BinanceAPIService {

    @Value("${integration.binance.api.url:NONE}")
    private String binanceApiUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public BinancePriceDTO getPriceOfCoinSymbol(String symbol) {
        String url = binanceApiUrl + "ticker/price?symbol=" + symbol;
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

        String apiUrl = binanceApiUrl + "klines?symbol=" + symbol + "&interval=1h&startTime=" + startTime + "&endTime=" + endTime;

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

    public final OperationRepository operationRepository;

    public String processTransaction(ProcessTransactionDTO trx) {
        //Si el usuario 1 o el 2 cancela la operación,
        // no se le suma una operación realizada y
        // se le descuenta 20 puntos de la reputación.
        var operation = operationRepository.findById(trx.getOperationId()).get();
        var user = operation.getUser();
        if (trx.getProcessType().equals(ProcessTransactionDTO.ProcessAccion.CANCELAR)){
            if (operation.isCancelledByUser()){
                user.handleCancelledTransaction(operation);
            }
        /*
        * Si la operación es cancelada por el sistema debido a la diferencia de precios,
        *  no se le suma la operación a los usuarios
        * pero tampoco reciben descuento de puntos por cancelación.
        */
            //No hacer nada
        } else {
            //Si la operación se realiza dentro de los 30 minutos,
            // suman 10 puntos
            // si supera los 30 minutos suman 5.
            user.handleSuccesfulTransaction(operation);
        }
        //resuelvetodo user.processTransaction(operation); ??
        return "";
    }


}
