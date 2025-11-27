package hse.finance.command.accounts;

import hse.finance.command.Command;
import hse.finance.facade.BankAccountFacade;

import java.math.BigDecimal;

public class CreateAccountCommand implements Command<Void> {
    private final BankAccountFacade facade;
    private final String name;
    private final BigDecimal balance;

    public CreateAccountCommand(BankAccountFacade facade, String name, BigDecimal balance) {
        this.facade = facade;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public Void execute() {
        facade.create(name, balance);
        System.out.println("Account created.");
        return null;
    }

    @Override
    public String name() { return "CreateAccount"; }
}
