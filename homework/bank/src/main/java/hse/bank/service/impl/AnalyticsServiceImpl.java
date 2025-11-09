package hse.bank.service.impl;

import hse.bank.domain.Operation;
import hse.bank.enums.OperationType;
import hse.bank.dto.AnalyticsReport;
import hse.bank.repository.OperationRepository;
import hse.bank.service.AnalyticsService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AnalyticsServiceImpl implements AnalyticsService {
    private final OperationRepository operationRepository;

    public AnalyticsServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public AnalyticsReport calculateAnalytics(LocalDate from, LocalDate to) {
        List<Operation> operations;

        if (from == null && to == null) {
            operations = operationRepository.findAll();
        } else {
            operations = operationRepository.findByDateBetween(from, to);
        }

        BigDecimal totalIncome = calculateTotal(operations, OperationType.INCOME);
        BigDecimal totalExpense = calculateTotal(operations, OperationType.EXPENSE);

        return new AnalyticsReport(from, to, totalIncome, totalExpense);
    }

    private BigDecimal calculateTotal(List<Operation> operations, OperationType type) {
        return operations.stream()
                .filter(op -> op.getType() == type)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}