package ar.edu.unq.desapp.grupod.backenddesappapi.model.dto;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntentionDTO {
    private Long intentionId;
    private LocalDateTime creationDateTime;
    private Long userId;
    private OperationType operationType;
    private Long cryptoActiveId;
    private double pesosAmount;

    public static IntentionDTO toDto(Intention intention) {
        IntentionDTO dto = new IntentionDTO();
        dto.setIntentionId(intention.getIntentionId());
        dto.setCreationDateTime(intention.getCreationDateTime());
        dto.setUserId(intention.getUser().getId());
        dto.setOperationType(intention.getOperationType());
        dto.setCryptoActiveId(intention.getCryptoActive().getActiveId());
        dto.setPesosAmount(intention.getPesosAmount());
        return dto;
    }

    public String getFormattedCreationDateTime() {
        return creationDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
