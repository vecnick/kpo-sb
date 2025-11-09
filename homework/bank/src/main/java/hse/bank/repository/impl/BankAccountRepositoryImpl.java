package hse.bank.repository.impl;

import hse.bank.domain.BankAccount;
import hse.bank.repository.BankAccountRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BankAccountRepositoryImpl implements BankAccountRepository {
    private final Map<Long, BankAccount> storage = new ConcurrentHashMap<>();

    @Override
    public BankAccount save(BankAccount account) {
        storage.put(account.getId(), account);
        return account;
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<BankAccount> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(BankAccount::getId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public void clear() {
        storage.clear();
    }
}