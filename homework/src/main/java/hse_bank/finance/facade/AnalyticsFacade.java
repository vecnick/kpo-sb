package hse_bank.finance.facade;

import hse_bank.finance.domain.CategoryType;
import hse_bank.finance.domain.Operation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class AnalyticsFacade {
    private final OperationFacade operationFacade;

    public AnalyticsFacade(OperationFacade operationFacade) {
        this.operationFacade = operationFacade;
    }

    public double calculateBalanceDifference(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = operationFacade.getAllOperations().stream()
                .filter(op -> !op.getDate().isBefore(start) && !op.getDate().isAfter(end))
                .toList();

        double totalIncome = operations.stream()
                .filter(op -> op.getType() == CategoryType.INCOME)
                .mapToDouble(Operation::getAmount)
                .sum();

        double totalExpense = operations.stream()
                .filter(op -> op.getType() == CategoryType.EXPENSE)
                .mapToDouble(Operation::getAmount)
                .sum();

        return totalIncome - totalExpense;
    }

    public Map<UUID, Double> groupOperationsByCategory(CategoryType type) {
        return operationFacade.getAllOperations().stream()
                .filter(op -> op.getType() == type)
                .collect(Collectors.groupingBy(
                        Operation::getCategoryId,
                        Collectors.summingDouble(Operation::getAmount)
                ));
    }

    // Дополнительная аналитика
    public double getTotalIncome() {
        return operationFacade.getAllOperations().stream()
                .filter(op -> op.getType() == CategoryType.INCOME)
                .mapToDouble(Operation::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return operationFacade.getAllOperations().stream()
                .filter(op -> op.getType() == CategoryType.EXPENSE)
                .mapToDouble(Operation::getAmount)
                .sum();
    }

    public double getCurrentBalance() {
        return getTotalIncome() - getTotalExpense();
    }
}