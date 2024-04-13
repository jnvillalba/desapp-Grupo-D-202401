package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoExchange {

    public void showCryptoActive(List<String> crypto) {
    //muestra las cotizaciones de los criptoActivos "crypto"
    }

    public void showLast24HsPrices(String crypto, LocalDateTime timestamp, float price) {
        // Implementación para mostrar las cotizaciones de las últimas 24 horas del criptoactivo dado con dia y hora de la cotizacion y el precio del criptoactivo en ese momento
    }

    public void buyOrSellCrypto(String crypto, float amount, float cryptoPrice, float totalAmountToPay, User user, String operation) {
        //vender/comprar una determinada cantidad de crypto que esta a un determinado precio

        /*
        crypto: crypto que se va a vender/comprar
        amount: cantidad de crypto a vender/comprar
        cryptoPrice: precio del cryptoActive
        totalAmountToPay: monto total a pagar
        user: usuario que realiza la venta/compra
        operation: tipo de operacion: venta o compra
        */
    }

    public void listActiveIntentions(User user, LocalDateTime currentDate) {
        // listado de intenciones activas de compra/venta (intenciones que expresan la intención de comprar o vender de un usuario)
    }

    public void processTransaction(User user1, User user2, String crypto, float amount, float cryptoPrice, float totalAmountToPay, String transactionType, String shippingAddress) {
        // procesar transacción informada por un usuario
    }

    public void reportTradingVolume(User user, LocalDateTime startDate, LocalDateTime endDate) {
        // informar volumen operado de criptoactivos entre dos fechas
    }
}
