//package ar.edu.unq.desapp.grupoD.backenddesappapi.webservice;
//
//import ar.edu.unq.desapp.grupoD.backenddesappapi.model.CriptoActive;
//import ar.edu.unq.desapp.grupoD.backenddesappapi.service.binance.BinanceAPIService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@Transactional
//@RequestMapping("/api/crypto")
//public class CryptoExchangeController {
//
//    @Autowired
//    BinanceAPIService binanceAPIService;
//
//    @GetMapping("/crypto/{symbol}")
//    public ResponseEntity<CriptoActive> getCryptoCurrencyValue(@PathVariable String symbol) {
//        CriptoActive entity = binanceAPIService.getPriceOfCoinSymbol(symbol);
//        return ResponseEntity.ok().body(entity);
//    }
//
//    @GetMapping("/crypto/prices")
//    public ResponseEntity<List<CriptoActive>> getPricesOfCoins() {
//        return ResponseEntity.ok().body(binanceAPIService.getPricesOfCoins());
//    }
//
//    @GetMapping("/crypto/last24HrsPrices/{symbol}")
//    public ResponseEntity<List<CriptoActive>> last24HrsPrices(@PathVariable String symbol) {
//        return ResponseEntity.ok().body(binanceAPIService.last24HrsPrices(symbol));
//    }
//
//}
