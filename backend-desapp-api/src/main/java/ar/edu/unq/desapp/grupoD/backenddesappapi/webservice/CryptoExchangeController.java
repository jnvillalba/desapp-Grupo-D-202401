package ar.edu.unq.desapp.grupoD.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ExpressIntentionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.BinanceAPIService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.IntentionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/crypto")
@AllArgsConstructor
public class CryptoExchangeController {

    private final BinanceAPIService binanceAPIService;
    private final IntentionService intentionService;

    @GetMapping("/crypto/{symbol}")
    public ResponseEntity<BinancePriceDTO> getCryptoCurrencyValue(@PathVariable String symbol) {
        BinancePriceDTO entity = binanceAPIService.getPriceOfCoinSymbol(symbol);
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping("/crypto/prices")
    public ResponseEntity<List<BinancePriceDTO>> getPricesOfCoins() {
        return ResponseEntity.ok().body(binanceAPIService.getPricesOfCoins());
    }

    @GetMapping("/crypto/last24HrsPrices/{symbol}")
    public ResponseEntity<List<BinancePriceDTO>> last24HrsPrices(@PathVariable String symbol) {
        return ResponseEntity.ok().body(binanceAPIService.last24HrsPrices(symbol));
    }

    @PostMapping("/intentions")
    public ResponseEntity<String> expressIntention(@Valid @RequestBody ExpressIntentionDTO expressIntentionDTO) {
        //TODO
        //User user = getCurrentUser();

         Intention intention = intentionService.expressIntention(
                user,
                expressIntentionDTO
        );

         return ResponseEntity.status(HttpStatus.CREATED).body("Intention expressed successfully");
    }
}
