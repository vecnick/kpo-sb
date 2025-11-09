package hse.bank.dto;

import hse.bank.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class CreateOperationRequest {
    private final OperationType type;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;
    private final long accountId;
    private final long categoryId;
}