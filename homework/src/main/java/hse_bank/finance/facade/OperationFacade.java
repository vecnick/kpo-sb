package hse_bank.finance.facade;

import hse_bank.finance.domain.CategoryType;
import hse_bank.finance.domain.Operation;
import hse_bank.finance.factories.DomainFactory;
import hse_bank.finance.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OperationFacade {
    private final Repository<Operation> repository;
    private final DomainFactory factory;
    private final BankAccountFacade bankAccountFacade;

    public OperationFacade(Repository<Operation> repository, DomainFactory factory, BankAccountFacade bankAccountFacade) {
        this.repository = repository;
        this.factory = factory;
        this.bankAccountFacade = bankAccountFacade;
    }

    public Operation createOperation(CategoryType type, UUID bankAccountId, double amount,
                                     LocalDateTime date, String description, UUID categoryId) {
        // Проверяем существование счета
        bankAccountFacade.getAccount(bankAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found: " + bankAccountId));

        Operation operation = factory.createOperation(type, bankAccountId, amount, date, description, categoryId);

        // Обновляем баланс счета
        updateAccountBalance(bankAccountId, type, amount);

        repository.save(operation);
        return operation;
    }

    private void updateAccountBalance(UUID accountId, CategoryType type, double amount) {
        bankAccountFacade.getAccount(accountId).ifPresent(account -> {
            double currentBalance = account.getBalance();
            if (type == CategoryType.INCOME) {
                account.setBalance(currentBalance + amount);
            } else {
                account.setBalance(currentBalance - amount);
            }
            // Сохраняем обновленный счет
            bankAccountFacade.saveAccount(account);
        });
    }

    public Optional<Operation> getOperation(UUID id) {
        return repository.findById(id);
    }

    public List<Operation> getAllOperations() {
        return repository.findAll();
    }

    public List<Operation> getOperationsByAccount(UUID accountId) {
        return repository.findAll().stream()
                .filter(op -> op.getBankAccountId().equals(accountId))
                .toList();
    }

    public List<Operation> getOperationsByCategory(UUID categoryId) {
        return repository.findAll().stream()
                .filter(op -> op.getCategoryId().equals(categoryId))
                .toList();
    }

    public List<Operation> getOperationsByType(CategoryType type) {
        return repository.findAll().stream()
                .filter(op -> op.getType() == type)
                .toList();
    }

    public List<Operation> getOperationsByDateRange(LocalDateTime start, LocalDateTime end) {
        return repository.findAll().stream()
                .filter(op -> !op.getDate().isBefore(start) && !op.getDate().isAfter(end))
                .toList();
    }

    public void deleteOperation(UUID id) {
        repository.findById(id).ifPresent(operation -> {
            // Восстанавливаем баланс при удалении операции
            UUID accountId = operation.getBankAccountId();
            double amount = operation.getAmount();
            CategoryType type = operation.getType();

            bankAccountFacade.getAccount(accountId).ifPresent(account -> {
                double currentBalance = account.getBalance();
                if (type == CategoryType.INCOME) {
                    account.setBalance(currentBalance - amount);
                } else {
                    account.setBalance(currentBalance + amount);
                }
                // Сохраняем обновленный счет
                bankAccountFacade.saveAccount(account);
            });

            repository.delete(id);
        });
    }

    public void updateOperation(UUID id, String newDescription) {
        repository.findById(id).ifPresent(operation -> {
            operation.setDescription(newDescription);
            repository.save(operation);
        });
    }

    public double getTotalAmountByAccount(UUID accountId) {
        return getOperationsByAccount(accountId).stream()
                .mapToDouble(op -> {
                    if (op.getType() == CategoryType.INCOME) {
                        return op.getAmount();
                    } else {
                        return -op.getAmount();
                    }
                })
                .sum();
    }

    public double getTotalAmountByCategory(UUID categoryId) {
        return getOperationsByCategory(categoryId).stream()
                .mapToDouble(Operation::getAmount)
                .sum();
    }
}