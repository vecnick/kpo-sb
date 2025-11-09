package hse.bank.facade;

import hse.bank.domain.*;
import hse.bank.dto.*;
import hse.bank.service.*;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FinanceFacade {
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final OperationService operationService;
    private final AnalyticsService analyticsService;

    public BankAccount createAccount(CreateAccountRequest request) {
        return accountService.createAccount(request.getName());
    }

    public void deleteAccount(DeleteAccountRequest request) {
        accountService.deleteAccount(request.getId());
    }

    public Category createCategory(CreateCategoryRequest request) {
        return categoryService.createCategory(request.getName(), request.getType());
    }

    public void deleteCategory(DeleteCategoryRequest request) {
        categoryService.deleteCategory(request.getId());
    }

    public Operation addOperation(Operation operation) {
        return operationService.addOperation(operation);
    }

    public void deleteOperation(DeleteOperationRequest request) {
        operationService.deleteOperation(request.getId());
    }

    public List<BankAccount> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public List<Operation> getAllOperations() {
        return operationService.getAllOperations();
    }

    public AnalyticsReport getAnalytics(LocalDate from, LocalDate to) {
        return analyticsService.calculateAnalytics(from, to);
    }
}