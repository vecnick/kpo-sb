package hse_bank.finance.factories;

import hse_bank.finance.domain.BankAccount;
import hse_bank.finance.domain.Category;
import hse_bank.finance.domain.CategoryType;
import hse_bank.finance.domain.Operation;

import java.time.LocalDateTime;
import java.util.UUID;

public class DomainFactory {

    public BankAccount createBankAccount(String name, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty");
        }
        return new BankAccount(UUID.randomUUID(), name, initialBalance);
    }

    public Category createCategory(CategoryType type, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        return new Category(UUID.randomUUID(), type, name);
    }

    public Operation createOperation(CategoryType type, UUID bankAccountId, double amount,
                                     LocalDateTime date, String description, UUID categoryId) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Operation amount must be positive");
        }
        if (bankAccountId == null || categoryId == null) {
            throw new IllegalArgumentException("Bank account and category must be specified");
        }
        if (date == null) {
            date = LocalDateTime.now();
        }
        return new Operation(UUID.randomUUID(), type, bankAccountId, amount, date, description, categoryId);
    }
}