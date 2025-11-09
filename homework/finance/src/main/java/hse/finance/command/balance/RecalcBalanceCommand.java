package hse.finance.command.balance;

import hse.finance.command.Command;
import hse.finance.service.BalanceService;

public class RecalcBalanceCommand implements Command<Void> {
    private final BalanceService balanceService;

    public RecalcBalanceCommand(BalanceService balanceService) { this.balanceService = balanceService; }

    @Override
    public Void execute() {
        balanceService.recalcAll();
        System.out.println("Balance recalculated.");
        return null;
    }

    @Override
    public String name() { return "RecalcBalance"; }
}
