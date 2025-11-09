package hse.bank.domain;

import hse.bank.io.export.DataVisitor;
import hse.bank.io.export.Visitable;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class BankAccount implements Visitable {
    private final Long id;
    private BigDecimal balance;

    @Setter
    private String name;

    @Override
    public void accept(DataVisitor visitor) {
        visitor.visit(this);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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