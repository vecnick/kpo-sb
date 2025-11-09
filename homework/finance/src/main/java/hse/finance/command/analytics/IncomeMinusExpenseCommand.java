package hse.finance.command.analytics;

import hse.finance.command.Command;
import hse.finance.facade.AnalyticsFacade;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeMinusExpenseCommand implements Command<BigDecimal> {
    private final AnalyticsFacade analytics;
    private final LocalDate from;
    private final LocalDate to;

    public IncomeMinusExpenseCommand(AnalyticsFacade analytics, LocalDate from, LocalDate to) {
        this.analytics = analytics;
        this.from = from;
        this.to = to;
    }

    @Override
    public BigDecimal execute() {
        var diff = analytics.incomeMinusExpense(from, to);
        System.out.println("Income minus expenses: " + diff);
        return diff;
    }

    @Override
    public String name() { return "IncomeMinusExpense"; }
}
