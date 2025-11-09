package hse.finance.facade;

import hse.finance.domain.Factory;
import hse.finance.domain.Operation;
import hse.finance.domain.OperationType;
import hse.finance.repository.BankAccountRepository;
import hse.finance.repository.CategoryRepository;
import hse.finance.repository.OperationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OperationFacade {
    private final OperationRepository operations;
    private final BankAccountRepository accounts;
    private final CategoryRepository categories;
    private final Factory factory;

    public OperationFacade(OperationRepository operations,
                           BankAccountRepository accounts,
                           CategoryRepository categories,
                           Factory factory) {
        this.operations = operations;
        this.accounts = accounts;
        this.categories = categories;
        this.factory = factory;
    }

    public Operation create(OperationType type, UUID accountId, BigDecimal amount, LocalDate date, String description, UUID categoryId) {
        // Validate references exist
        accounts.findById(accountId).orElseThrow();
        categories.findById(categoryId).orElseThrow();
        var op = factory.createOperation(type, accountId, amount, date, description, categoryId);
        return operations.save(op);
    }

    public Optional<Operation> get(UUID id) { return operations.findById(id); }

    public List<Operation> list() { return operations.findAll(); }

    public void delete(UUID id) { operations.deleteById(id); }
}

