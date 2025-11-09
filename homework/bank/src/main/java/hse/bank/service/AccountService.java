package hse.bank.service;

import hse.bank.domain.BankAccount;
import hse.bank.domain.Operation;

import java.util.List;

public interface AccountService {
    BankAccount createAccount(String name);
    BankAccount getAccountById(Long id);
    List<BankAccount> getAllAccounts();
    void updateAccountName(Long id, String newName);
    void deleteAccount(Long id);

    void applyOperation(Operation operation);
}