package hse.finance.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class BankAccount implements Identifiable {
    private final UUID id;
    private String name;
    private BigDecimal balance;

    public BankAccount(UUID id, String name, BigDecimal balance) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.balance = Objects.requireNonNull(balance, "balance");
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = Objects.requireNonNull(balance);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
