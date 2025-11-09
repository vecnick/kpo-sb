package hse.bank.command;

import hse.bank.domain.Operation;
import hse.bank.dto.CreateOperationRequest;
import hse.bank.facade.FinanceFacade;
import hse.bank.factories.DomainFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddOperationCommand implements Command {
    private final FinanceFacade financeFacade;
    private final DomainFactory domainFactory;
    private final CreateOperationRequest request;

    @Override
    public void execute() {
        Operation operation = domainFactory.createOperation(request);
        Operation createdOperation = financeFacade.addOperation(operation);
        System.out.println("Operation added successfully: " + createdOperation);
    }
}