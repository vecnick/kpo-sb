package hse.bank.service;

import hse.bank.domain.Category;
import hse.bank.enums.OperationType;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name, OperationType type);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void updateCategoryName(Long id, String newName);
    void deleteCategory(Long id);
}