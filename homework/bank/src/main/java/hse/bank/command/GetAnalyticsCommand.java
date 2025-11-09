package hse.bank.command;

import hse.bank.dto.AnalyticsReport;
import hse.bank.facade.FinanceFacade;

import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetAnalyticsCommand implements Command {
    private final FinanceFacade financeFacade;
    private final LocalDate from;
    private final LocalDate to;

    @Override
    public void execute() {
        AnalyticsReport report = financeFacade.getAnalytics(from, to);
        System.out.println(report);
    }
}