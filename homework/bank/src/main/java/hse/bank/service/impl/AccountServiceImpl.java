package hse.bank.service.impl;

import hse.bank.domain.BankAccount;
import hse.bank.domain.Operation;
import hse.bank.enums.OperationType;
import hse.bank.exceptions.BusinessLogicException;
import hse.bank.exceptions.ValidationException;
import hse.bank.repository.BankAccountRepository;
import hse.bank.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final BankAccountRepository accountRepository;

    public AccountServiceImpl(BankAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public BankAccount createAccount(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Account name cannot be empty");
        }
        BankAccount account = new BankAccount(
                System.currentTimeMillis(),
                BigDecimal.ZERO,
                name
        );
        return accountRepository.save(account);
    }

    @Override
    public BankAccount getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Account not found: " + id));
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void updateAccountName(Long id, String newName) {
        BankAccount account = getAccountById(id);
        account.setName(newName);
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void applyOperation(Operation operation) {
        BankAccount account = getAccountById(operation.getBankAccountId());

        if (operation.getType() == OperationType.INCOME) {
            account.deposit(operation.getAmount());
        } else {
            account.withdraw(operation.getAmount());
        }

        accountRepository.save(account);
    }
}