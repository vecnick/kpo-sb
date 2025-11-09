package hse.finance.command.accounts;

import hse.finance.command.Command;
import hse.finance.facade.BankAccountFacade;

public class ListAccountsCommand implements Command<Void> {
    private final BankAccountFacade facade;

    public ListAccountsCommand(BankAccountFacade facade) {
        this.facade = facade;
    }

    @Override
    public Void execute() {
        facade.list().forEach(System.out::println);
        return null;
    }

    @Override
    public String name() { return "ListAccounts"; }
}

