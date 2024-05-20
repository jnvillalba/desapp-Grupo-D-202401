package ar.edu.unq.desapp.grupoD.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.*;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.BinanceAPIService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.IntentionService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.TransactionService;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.UserService;
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
    private final UserService userService;
    private final TransactionService transactionService;
    @io.swagger.v3.oas.annotations.Operation(summary = "Get a cryptocurrency price")
    @GetMapping("/crypto/{symbol}")
    public ResponseEntity<BinancePriceDTO> getCryptoCurrencyValue(@PathVariable String symbol) {
        BinancePriceDTO entity = binanceAPIService.getPriceOfCoinSymbol(symbol);
        return ResponseEntity.ok().body(entity);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get all cryptocurrency prices")
    @GetMapping("/crypto/prices")
    public ResponseEntity<List<BinancePriceDTO>> getPricesOfCoins() {
        return ResponseEntity.ok().body(binanceAPIService.getPricesOfCoins());
    }

    @GetMapping("/crypto/last24HrsPrices/{symbol}")
    public ResponseEntity<List<BinancePriceDTO>> last24HrsPrices(@PathVariable String symbol) {
        return ResponseEntity.ok().body(binanceAPIService.last24HrsPrices(symbol));
    }
    @PostMapping("/operation/processTransaction")
    public ResponseEntity<String> processTransaction(@RequestBody ProcessTransactionDTO trx) {
        Operation operation = transactionService.processTransaction(trx);
        return ResponseEntity.ok().body(operation.getStatus().toString());
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Creates an intention")
    @PostMapping("/intention")
    public ResponseEntity<String> expressIntention(@Valid @RequestBody ExpressIntentionDTO expressIntentionDTO) {
        User user = userService.getUser(expressIntentionDTO.getUserId());

        Intention intention = intentionService.expressIntention(
                user,
                expressIntentionDTO
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("Intention expressed successfully");
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get all intentions")
    @GetMapping("/intentions")
    public ResponseEntity<List<Intention>> getAllIntentions() {
        return ResponseEntity.ok().body(intentionService.getAllIntentions());
    }

    @PostMapping("/operation/report")
    public ResponseEntity<OperationReportDTO> generateReport(@RequestBody RequestReportDTO request) {
        OperationReportDTO report = userService.generateReport(request);
        return ResponseEntity.ok().body(report);
    }
}
