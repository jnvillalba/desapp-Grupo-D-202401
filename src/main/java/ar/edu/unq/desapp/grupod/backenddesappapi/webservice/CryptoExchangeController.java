package ar.edu.unq.desapp.grupod.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.BinanceAPIService;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.IntentionService;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.TransactionService;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.UserService;
import ar.edu.unq.desapp.grupod.backenddesappapi.utils.aspects.LogExecutionTime;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CryptoCurrency services", description = "Manage cryptocurrencies")
@RestController
@Transactional
@RequestMapping("/api/crypto")
@AllArgsConstructor
public class CryptoExchangeController {

    private final BinanceAPIService binanceAPIService;
    private final IntentionService intentionService;
    private final UserService userService;
    private final TransactionService transactionService;

    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Get a cryptocurrency price")
    @GetMapping("/crypto/{symbol}")
    public ResponseEntity<BinancePriceDTO> getCryptoCurrencyValue(@PathVariable String symbol) {
        BinancePriceDTO entity = binanceAPIService.getPriceOfCoinSymbol(symbol);
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping(path = "/crypto/evict-cache")
    public ResponseEntity<String> evictCache() {
        binanceAPIService.clearCache();
        return ResponseEntity.ok("Cache successfully clean");
    }

    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Get all cryptocurrency prices")
    @GetMapping("/crypto/prices")
    public ResponseEntity<List<BinancePriceDTO>> getPricesOfCoins() {
        return ResponseEntity.ok().body(binanceAPIService.getPricesOfCoins());
    }

    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Get a cryptocurrency last 24Hrs Prices")
    @GetMapping("/crypto/last24HrsPrices/{symbol}")
    public ResponseEntity<List<BinancePriceDTO>> last24HrsPrices(@PathVariable String symbol) {
        return ResponseEntity.ok().body(binanceAPIService.last24HrsPrices(symbol));
    }

    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Get a cryptocurrency last 10 minutes Prices")
    @GetMapping("/crypto/last10MinPrices/{symbol}")
    public ResponseEntity<List<BinancePriceDTO>> last10MinPrices(@PathVariable String symbol) {
        return ResponseEntity.ok().body(binanceAPIService.last10MinPrices(symbol));
    }

    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Process the transaction reported by a user")
    @PostMapping("/operation/processTransaction")
    public ResponseEntity<String> processTransaction(@RequestBody ProcessTransactionDTO trx) {
        Operation operation = transactionService.processTransaction(trx);
        return ResponseEntity.ok().body(operation.getStatus().toString());
    }

    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Creates an intention")
    @PostMapping("/intention")
    public ResponseEntity<String> expressIntention(@Valid @RequestBody ExpressIntentionDTO expressIntentionDTO) {
        User user = userService.getUser(expressIntentionDTO.getUserId());

        Intention intention = intentionService.expressIntention(
                user,
                expressIntentionDTO
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                "Intention of " + intention.getOperationType()
                        + " expressed successfully");
    }


    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Get all intentions")
    @GetMapping("/intentions")
    public ResponseEntity<List<IntentionDTO>> getAllIntentions() {
        return ResponseEntity.ok().body(intentionService.getAllIntentions());
    }

    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Report the traded volume of crypto assets between two dates of a user.")
    @PostMapping("/operation/report")
    public ResponseEntity<OperationReportDTO> generateReport(@RequestBody RequestReportDTO request) {
        OperationReportDTO report = userService.generateReport(request);
        return ResponseEntity.ok().body(report);
    }

    @LogExecutionTime
    @io.swagger.v3.oas.annotations.Operation(summary = "Get all users")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
}

