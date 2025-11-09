package hse.finance.repository.proxy;

import hse.finance.domain.Operation;
import hse.finance.domain.OperationType;
import hse.finance.repository.OperationRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.UUID;

public class CachingOperationRepository implements OperationRepository {
    private final OperationRepository delegate;
    private final Map<UUID, Operation> cacheById = new ConcurrentHashMap<>();
    private final AtomicReference<List<Operation>> cacheAll = new AtomicReference<>();

    public CachingOperationRepository(OperationRepository delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public Operation save(Operation entity) {
        var saved = delegate.save(entity);
        cacheById.put(saved.getId(), saved);
        cacheAll.set(null);
        return saved;
    }

    @Override
    public Optional<Operation> findById(UUID id) {
        var cached = cacheById.get(id);
        if (cached != null) return Optional.of(cached);
        var found = delegate.findById(id);
        found.ifPresent(e -> cacheById.put(id, e));
        return found;
    }

    @Override
    public List<Operation> findAll() {
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

    @Override
    public List<Operation> findByBankAccountId(UUID bankAccountId) {
        // Use cache if available
        var all = cacheAll.get();
        if (all != null) return all.stream().filter(o -> o.getBankAccountId().equals(bankAccountId)).collect(Collectors.toList());
        return delegate.findByBankAccountId(bankAccountId);
    }

    @Override
    public List<Operation> findByCategoryId(UUID categoryId) {
        var all = cacheAll.get();
        if (all != null) return all.stream().filter(o -> o.getCategoryId().equals(categoryId)).collect(Collectors.toList());
        return delegate.findByCategoryId(categoryId);
    }

    @Override
    public List<Operation> findByDateRange(LocalDate from, LocalDate to) {
        var all = cacheAll.get();
        if (all != null) return all.stream().filter(o -> !o.getDate().isBefore(from) && !o.getDate().isAfter(to)).collect(Collectors.toList());
        return delegate.findByDateRange(from, to);
    }

    @Override
    public List<Operation> findByType(OperationType type) {
        var all = cacheAll.get();
        if (all != null) return all.stream().filter(o -> o.getType() == type).collect(Collectors.toList());
        return delegate.findByType(type);
    }
}

