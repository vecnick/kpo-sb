package hse_bank.finance.facade;

import hse_bank.finance.domain.BankAccount;
import hse_bank.finance.factories.DomainFactory;
import hse_bank.finance.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BankAccountFacade {
    private final Repository<BankAccount> repository;
    private final DomainFactory factory;

    public BankAccountFacade(Repository<BankAccount> repository, DomainFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public BankAccount createAccount(String name, double initialBalance) {
        BankAccount account = factory.createBankAccount(name, initialBalance);
        repository.save(account);
        return account;
    }

    public Optional<BankAccount> getAccount(UUID id) {
        return repository.findById(id);
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public Optional<BankAccount> getFirstAccount() {
        List<BankAccount> accounts = getAllAccounts();
        return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts.get(0));
    }

    public void updateAccount(UUID id, String newName) {
        repository.findById(id).ifPresent(account -> {
            account.setName(newName);
            repository.save(account);
        });
    }

    public void deleteAccount(UUID id) {
        repository.delete(id);
    }

    public void saveAccount(BankAccount account) {
        repository.save(account);
    }
}