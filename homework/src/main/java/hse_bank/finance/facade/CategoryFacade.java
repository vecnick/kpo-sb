package hse_bank.finance.facade;

import hse_bank.finance.domain.Category;
import hse_bank.finance.domain.CategoryType;
import hse_bank.finance.factories.DomainFactory;
import hse_bank.finance.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryFacade {
    private final Repository<Category> repository;
    private final DomainFactory factory;

    public CategoryFacade(Repository<Category> repository, DomainFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public Category createCategory(CategoryType type, String name) {
        Category category = factory.createCategory(type, name);
        repository.save(category);
        return category;
    }

    public Optional<Category> getCategory(UUID id) {
        return repository.findById(id);
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public void updateCategory(UUID id, String newName) {
        repository.findById(id).ifPresent(category -> {
            category.setName(newName);
            repository.save(category);
        });
    }

    public void deleteCategory(UUID id) {
        repository.delete(id);
    }

    public List<Category> getCategoriesByType(CategoryType type) {
        return repository.findAll().stream()
                .filter(category -> category.getType() == type)
                .toList();
    }
}