package ar.edu.unq.desapp.grupoD.backenddesappapi.persistence.binance.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BinancePriceDTO {
        private String symbol;
        private String price;
}
