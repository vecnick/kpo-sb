package hse.bank.service;

import hse.bank.domain.Operation;

import java.util.List;

public interface OperationService {
    Operation addOperation(Operation operation);
    Operation getOperationById(Long id);
    List<Operation> getAllOperations();
    void deleteOperation(Long id);
}