package hse.bank.command;

import hse.bank.domain.BankAccount;
import hse.bank.dto.CreateAccountRequest;
import hse.bank.facade.FinanceFacade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateAccountCommand implements Command {
    private final FinanceFacade financeFacade;
    private final CreateAccountRequest request;

    @Override
    public void execute() {
        BankAccount account = financeFacade.createAccount(request);
        System.out.println("Account created successfully: " + account);
    }
}