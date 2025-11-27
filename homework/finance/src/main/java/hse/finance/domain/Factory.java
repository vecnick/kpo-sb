package hse.finance.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

// Simple Factory ensuring validation and single creation point.
public class Factory {
    public BankAccount createBankAccount(String name, BigDecimal balance) {
        if (name == null || name.isBlank()) throw new ValidationException("BankAccount.name is blank");
        if (balance == null) throw new ValidationException("BankAccount.balance is null");
        if (balance.compareTo(BigDecimal.ZERO) < 0) throw new ValidationException("BankAccount.balance < 0");
        return new BankAccount(UUID.randomUUID(), name.trim(), balance);
        }

    public Category createCategory(CategoryType type, String name) {
        Objects.requireNonNull(type, "Category.type");
        if (name == null || name.isBlank()) throw new ValidationException("Category.name is blank");
        return new Category(UUID.randomUUID(), type, name.trim());
    }

    public Operation createOperation(OperationType type,
                                     UUID bankAccountId,
                                     BigDecimal amount,
                                     LocalDate date,
                                     String description,
                                     UUID categoryId) {
        Objects.requireNonNull(type, "Operation.type");
        Objects.requireNonNull(bankAccountId, "Operation.bankAccountId");
        Objects.requireNonNull(amount, "Operation.amount");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException("Operation.amount must be > 0");
        Objects.requireNonNull(date, "Operation.date");
        Objects.requireNonNull(categoryId, "Operation.categoryId");
        return new Operation(UUID.randomUUID(), type, bankAccountId, amount, date, description, categoryId);
    }
}

