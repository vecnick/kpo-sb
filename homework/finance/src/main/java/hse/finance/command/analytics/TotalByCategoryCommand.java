package hse.finance.command.analytics;

import hse.finance.command.Command;
import hse.finance.domain.Category;
import hse.finance.facade.AnalyticsFacade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class TotalByCategoryCommand implements Command<Map<Category, BigDecimal>> {
    private final AnalyticsFacade analytics;
    private final LocalDate from;
    private final LocalDate to;

    public TotalByCategoryCommand(AnalyticsFacade analytics, LocalDate from, LocalDate to) {
        this.analytics = analytics;
        this.from = from;
        this.to = to;
    }

    @Override
    public Map<Category, BigDecimal> execute() {
        var map = analytics.totalByCategory(from, to);
        map.forEach((c, sum) -> System.out.println(c + " -> " + sum));
        return map;
    }

    @Override
    public String name() { return "TotalByCategory"; }
}

