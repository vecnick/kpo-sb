package hse_bank.finance.commands;

import hse_bank.finance.domain.BankAccount;
import hse_bank.finance.facade.BankAccountFacade;

public class CreateBankAccountCommand implements Command {
    private final BankAccountFacade facade;
    private final String name;
    private final double initialBalance;
    private BankAccount createdAccount;

    public CreateBankAccountCommand(BankAccountFacade facade, String name, double initialBalance) {
        this.facade = facade;
        this.name = name;
        this.initialBalance = initialBalance;
    }

    @Override
    public void execute() {
        createdAccount = facade.createAccount(name, initialBalance);
        System.out.println("Created bank account: " + createdAccount.getName() + " with ID: " + createdAccount.getId());
    }

    @Override
    public void undo() {
        if (createdAccount != null) {
            facade.deleteAccount(createdAccount.getId());
            System.out.println("Undo: Deleted bank account: " + createdAccount.getName());
        }
    }

    public BankAccount getCreatedAccount() {
        return createdAccount;
    }
}