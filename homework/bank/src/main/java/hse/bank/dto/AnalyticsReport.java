package hse.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class AnalyticsReport {
    private final LocalDate from;
    private final LocalDate to;
    private final BigDecimal totalIncome;
    private final BigDecimal totalExpense;
    private final BigDecimal difference;

    public AnalyticsReport(LocalDate from, LocalDate to, BigDecimal totalIncome, BigDecimal totalExpense) {
        this.from = from;
        this.to = to;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.difference = totalIncome.subtract(totalExpense);
    }

    @Override
    public String toString() {
        return "AnalyticsReport (from " + from + " to " + to + "):" +
                "\n  Total Income: " + totalIncome +
                "\n  Total Expense: " + totalExpense +
                "\n  Difference (Income - Expense): " + difference;
    }
}