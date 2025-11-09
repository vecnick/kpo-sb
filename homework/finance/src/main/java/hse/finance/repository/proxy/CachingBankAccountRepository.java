package hse.finance.repository.proxy;

import hse.finance.domain.BankAccount;
import hse.finance.repository.BankAccountRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.UUID;

public class CachingBankAccountRepository implements BankAccountRepository {
    private final BankAccountRepository delegate;
    private final Map<UUID, BankAccount> cacheById = new ConcurrentHashMap<>();
    private final AtomicReference<List<BankAccount>> cacheAll = new AtomicReference<>();

    public CachingBankAccountRepository(BankAccountRepository delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public BankAccount save(BankAccount entity) {
        var saved = delegate.save(entity);
        cacheById.put(saved.getId(), saved);
        cacheAll.set(null);
        return saved;
    }

    @Override
    public Optional<BankAccount> findById(UUID id) {
        var cached = cacheById.get(id);
        if (cached != null) return Optional.of(cached);
        var found = delegate.findById(id);
        found.ifPresent(acc -> cacheById.put(id, acc));
        return found;
    }

    @Override
    public List<BankAccount> findAll() {
        var all = cacheAll.get();
        if (all != null) return all;
        all = delegate.findAll();
        cacheAll.set(all);
        return all;
    }

    @Override
    public void deleteById(UUID id) {
        delegate.deleteById(id);
        cacheById.remove(id);
        cacheAll.set(null);
    }
}

