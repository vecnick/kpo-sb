package hse.finance.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryValidationTest {

    private final Factory factory = new Factory();

    @Test
    void createBankAccount_negativeBalance_throws() {
        assertThrows(ValidationException.class, () -> factory.createBankAccount("Test", new BigDecimal("-1")));
    }

    @Test
    void createCategory_blankName_throws() {
        assertThrows(ValidationException.class, () -> factory.createCategory(CategoryType.INCOME, " "));
    }

    @Test
    void createOperation_nonPositiveAmount_throws() {
        UUID acc = UUID.randomUUID();
        UUID cat = UUID.randomUUID();
        assertThrows(ValidationException.class, () -> factory.createOperation(
                OperationType.EXPENSE, acc, BigDecimal.ZERO, LocalDate.now(), null, cat));
        assertThrows(ValidationException.class, () -> factory.createOperation(
                OperationType.INCOME, acc, new BigDecimal("-10"), LocalDate.now(), null, cat));
    }
}

