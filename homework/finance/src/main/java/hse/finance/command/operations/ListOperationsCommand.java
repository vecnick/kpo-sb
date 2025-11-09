package hse.finance.command.operations;

import hse.finance.command.Command;
import hse.finance.facade.OperationFacade;

public class ListOperationsCommand implements Command<Void> {
    private final OperationFacade facade;

    public ListOperationsCommand(OperationFacade facade) { this.facade = facade; }

    @Override
    public Void execute() {
        facade.list().forEach(System.out::println);
        return null;
    }

    @Override
    public String name() { return "ListOperations"; }
}

