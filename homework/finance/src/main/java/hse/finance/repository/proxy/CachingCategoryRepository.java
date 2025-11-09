package hse.finance.repository.proxy;

import hse.finance.domain.Category;
import hse.finance.repository.CategoryRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.UUID;

public class CachingCategoryRepository implements CategoryRepository {
    private final CategoryRepository delegate;
    private final Map<UUID, Category> cacheById = new ConcurrentHashMap<>();
    private final AtomicReference<List<Category>> cacheAll = new AtomicReference<>();

    public CachingCategoryRepository(CategoryRepository delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public Category save(Category entity) {
        var saved = delegate.save(entity);
        cacheById.put(saved.getId(), saved);
        cacheAll.set(null);
        return saved;
    }

    @Override
    public Optional<Category> findById(UUID id) {
        var cached = cacheById.get(id);
        if (cached != null) return Optional.of(cached);
        var found = delegate.findById(id);
        found.ifPresent(e -> cacheById.put(id, e));
        return found;
    }

    @Override
    public List<Category> findAll() {
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

