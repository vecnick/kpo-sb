package hse.finance.facade;

import hse.finance.domain.Category;
import hse.finance.domain.CategoryType;
import hse.finance.domain.Factory;
import hse.finance.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryFacade {
    private final CategoryRepository categories;
    private final Factory factory;

    public CategoryFacade(CategoryRepository categories, Factory factory) {
        this.categories = categories;
        this.factory = factory;
    }

    public Category create(CategoryType type, String name) {
        var cat = factory.createCategory(type, name);
        return categories.save(cat);
    }

    public Optional<Category> get(UUID id) { return categories.findById(id); }

    public List<Category> list() { return categories.findAll(); }

    public Category rename(UUID id, String newName) {
        var cat = categories.findById(id).orElseThrow();
        cat.setName(newName);
        return categories.save(cat);
    }

    public void delete(UUID id) { categories.deleteById(id); }
}

