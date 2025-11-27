package hse.finance.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Operation implements Identifiable {
    private final UUID id;
    private final OperationType type;
    private final UUID bankAccountId;
    private final UUID categoryId;
    private final LocalDate date;
    private final String description;
    private final BigDecimal amount;

    public Operation(UUID id,
                     OperationType type,
                     UUID bankAccountId,
                     BigDecimal amount,
                     LocalDate date,
                     String description,
                     UUID categoryId) {
        this.id = Objects.requireNonNull(id, "id");
        this.type = Objects.requireNonNull(type, "type");
        this.bankAccountId = Objects.requireNonNull(bankAccountId, "bankAccountId");
        this.amount = Objects.requireNonNull(amount, "amount");
        this.date = Objects.requireNonNull(date, "date");
        this.description = description; // nullable
        this.categoryId = Objects.requireNonNull(categoryId, "categoryId");
    }

    @Override
    public UUID getId() { return id; }
    public OperationType getType() { return type; }
    public UUID getBankAccountId() { return bankAccountId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
    public UUID getCategoryId() { return categoryId; }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", type=" + type +
                ", bankAccountId=" + bankAccountId +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
