package hse.finance.facade;

import hse.finance.domain.BankAccount;
import hse.finance.domain.Factory;
import hse.finance.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Facade for BankAccount operations
public class BankAccountFacade {
    private final BankAccountRepository accounts;
    private final Factory factory;

    public BankAccountFacade(BankAccountRepository accounts, Factory factory) {
        this.accounts = accounts;
        this.factory = factory;
    }

    public BankAccount create(String name, BigDecimal balance) {
        BankAccount acc = factory.createBankAccount(name, balance);
        return accounts.save(acc);
    }

    public Optional<BankAccount> get(UUID id) { return accounts.findById(id); }

    public List<BankAccount> list() { return accounts.findAll(); }

    public BankAccount updateName(UUID id, String name) {
        BankAccount acc = accounts.findById(id).orElseThrow();
        acc.setName(name);
        return accounts.save(acc);
    }

    public BankAccount updateBalance(UUID id, BigDecimal newBalance) {
        BankAccount acc = accounts.findById(id).orElseThrow();
        acc.setBalance(newBalance);
        return accounts.save(acc);
    }

    public void delete(UUID id) { accounts.deleteById(id); }
}

