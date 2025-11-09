package hse.bank.command;

import hse.bank.dto.DeleteOperationRequest;
import hse.bank.facade.FinanceFacade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteOperationCommand implements Command {
    private final FinanceFacade financeFacade;
    private final DeleteOperationRequest request;

    @Override
    public void execute() {
        financeFacade.deleteOperation(request);
        System.out.println("Operation deleted successfully: " + request.getId());
    }
}