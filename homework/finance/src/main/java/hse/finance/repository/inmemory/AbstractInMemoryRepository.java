package hse.finance.repository.inmemory;

import hse.finance.domain.Identifiable;
import hse.finance.repository.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.UUID;

public class AbstractInMemoryRepository<T extends Identifiable> implements Repository<T> {
    protected final ConcurrentMap<UUID, T> storage = new ConcurrentHashMap<>();

    @Override
    public T save(T entity) {
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}

