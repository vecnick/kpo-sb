package hse.bank.factories;

import hse.bank.domain.*;
import hse.bank.enums.OperationType;
import hse.bank.dto.CreateOperationRequest;
import hse.bank.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class DomainFactory {
    private final AtomicLong accountIdGenerator = new AtomicLong(0);
    private final AtomicLong categoryIdGenerator = new AtomicLong(0);
    private final AtomicLong operationIdGenerator = new AtomicLong(0);

    public BankAccount createBankAccount(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Account name cannot be empty.");
        }
        long id = accountIdGenerator.incrementAndGet();
        return new BankAccount(id, BigDecimal.ZERO, name);
    }

    public Category createCategory(String name, OperationType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Category name cannot be empty.");
        }
        if (type == null) {
            throw new ValidationException("Category type must be set.");
        }
        long id = categoryIdGenerator.incrementAndGet();
        return new Category(id, type, name);
    }

    public Operation createOperation(CreateOperationRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Operation amount must be positive.");
        }
        if (request.getType() == null) {
            throw new ValidationException("Operation type must be set.");
        }
        if (request.getDate() == null) {
            throw new ValidationException("Operation date must be set.");
        }

        long id = operationIdGenerator.incrementAndGet();
        return new Operation(
                id,
                request.getType(),
                request.getAccountId(),
                request.getCategoryId(),
                request.getAmount(),
                request.getDate(),
                request.getDescription()
        );
    }
}