package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoActive {

    private String symbol;
    private Float price;
    private String lastUpdateDateAndTime;
}
