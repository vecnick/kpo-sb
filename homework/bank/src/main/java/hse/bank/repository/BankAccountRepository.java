package hse.bank.repository;

import hse.bank.domain.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {
    BankAccount save(BankAccount account);
    Optional<BankAccount> findById(Long id);
    List<BankAccount> findAll();
    void deleteById(Long id);
    void clear();
}