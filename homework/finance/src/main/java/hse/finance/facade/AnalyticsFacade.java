package hse.finance.facade;

import hse.finance.domain.Category;
import hse.finance.domain.Operation;
import hse.finance.domain.OperationType;
import hse.finance.repository.CategoryRepository;
import hse.finance.repository.OperationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsFacade {
    private final OperationRepository operations;
    private final CategoryRepository categories;

    public AnalyticsFacade(OperationRepository operations, CategoryRepository categories) {
        this.operations = operations;
        this.categories = categories;
    }

    // a) Income minus expenses for a period
    public BigDecimal incomeMinusExpense(LocalDate from, LocalDate to) {
        var in = operations.findByType(OperationType.INCOME).stream()
                .filter(o -> !o.getDate().isBefore(from) && !o.getDate().isAfter(to))
                .map(Operation::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        var out = operations.findByType(OperationType.EXPENSE).stream()
                .filter(o -> !o.getDate().isBefore(from) && !o.getDate().isAfter(to))
                .map(Operation::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return in.subtract(out);
    }

    // b) Group incomes and expenses by category
    public Map<Category, BigDecimal> totalByCategory(LocalDate from, LocalDate to) {
        var byCatId = operations.findByDateRange(from, to).stream()
                .collect(Collectors.groupingBy(Operation::getCategoryId,
                        Collectors.mapping(Operation::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        Map<Category, BigDecimal> res = new LinkedHashMap<>();
        for (var e : byCatId.entrySet()) {
            categories.findById(e.getKey()).ifPresent(cat -> res.put(cat, e.getValue()));
        }
        return res;
    }

    // c) Additional analytics: average expense per category
    public Map<Category, BigDecimal> averageExpenseByCategory(LocalDate from, LocalDate to) {
        var filtered = operations.findByType(OperationType.EXPENSE).stream()
                .filter(o -> !o.getDate().isBefore(from) && !o.getDate().isAfter(to))
                .collect(Collectors.toList());
        Map<UUID, List<Operation>> opsByCat = filtered.stream().collect(Collectors.groupingBy(Operation::getCategoryId));
        Map<Category, BigDecimal> res = new LinkedHashMap<>();
        for (var e : opsByCat.entrySet()) {
            var ops = e.getValue();
            var sum = ops.stream().map(Operation::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            var avg = ops.isEmpty() ? BigDecimal.ZERO : sum.divide(BigDecimal.valueOf(ops.size()), 2, RoundingMode.HALF_UP);
            categories.findById(e.getKey()).ifPresent(cat -> res.put(cat, avg));
        }
        return res;
    }
}
