package hse.bank.command;

import hse.bank.dto.DeleteAccountRequest;
import hse.bank.facade.FinanceFacade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteAccountCommand implements Command {
    private final FinanceFacade financeFacade;
    private final DeleteAccountRequest request;

    @Override
    public void execute() {
        financeFacade.deleteAccount(request);
        System.out.println("Account deleted successfully: " + request.getId());
    }
}