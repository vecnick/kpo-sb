package hse.bank.service.impl;

import hse.bank.domain.Operation;
import hse.bank.exceptions.BusinessLogicException;
import hse.bank.repository.OperationRepository;
import hse.bank.service.AccountService;
import hse.bank.service.CategoryService;
import hse.bank.service.OperationService;

import java.util.List;

public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public OperationServiceImpl(OperationRepository operationRepository,
                                AccountService accountService,
                                CategoryService categoryService) {
        this.operationRepository = operationRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    public Operation addOperation(Operation operation) {
        try {
            accountService.applyOperation(operation);
        } catch (Exception e) {
            throw new BusinessLogicException("Failed to apply operation to account: " + e.getMessage());
        }

        return operationRepository.save(operation);
    }

    @Override
    public Operation getOperationById(Long id) {
        return operationRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Operation not found: " + id));
    }

    @Override
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public void deleteOperation(Long id) {
        operationRepository.deleteById(id);
    }
}