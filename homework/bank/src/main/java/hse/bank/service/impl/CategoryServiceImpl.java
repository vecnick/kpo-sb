package hse.bank.service.impl;

import hse.bank.domain.Category;
import hse.bank.enums.OperationType;
import hse.bank.exceptions.BusinessLogicException;
import hse.bank.exceptions.ValidationException;
import hse.bank.repository.CategoryRepository;
import hse.bank.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String name, OperationType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Category name cannot be empty");
        }
        if (type == null) {
            throw new ValidationException("Category type must be set");
        }

        Category category = new Category(
                System.currentTimeMillis(),
                type,
                name
        );
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Category not found: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void updateCategoryName(Long id, String newName) {
        Category category = getCategoryById(id);
        category.setName(newName);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}