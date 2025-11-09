package hse.bank.repository.impl;

import hse.bank.domain.Category;
import hse.bank.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CategoryRepositoryImpl implements CategoryRepository {
    private final Map<Long, Category> storage = new ConcurrentHashMap<>();

    @Override
    public Category save(Category category) {
        storage.put(category.getId(), category);
        return category;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Category> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(Category::getId))
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