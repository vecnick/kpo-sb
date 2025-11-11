package hse_bank.finance.repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T> implements Repository<T> {
    private final Map<UUID, T> storage = new ConcurrentHashMap<>();

    @Override
    public void save(T entity) {
        try {
            UUID id = getIdFromEntity(entity);
            storage.put(id, entity);
        } catch (Exception e) {
            throw new RuntimeException("Error saving entity", e);
        }
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
    public void delete(UUID id) {
        storage.remove(id);
    }

    private UUID getIdFromEntity(T entity) throws Exception {
        Field idField = entity.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        return (UUID) idField.get(entity);
    }
}