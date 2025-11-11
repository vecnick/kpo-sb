package hse_bank.finance.commands;

import hse_bank.finance.domain.CategoryType;
import hse_bank.finance.domain.Operation;
import hse_bank.finance.facade.OperationFacade;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateOperationCommand implements Command {
    private final OperationFacade facade;
    private final CategoryType type;
    private final UUID bankAccountId;
    private final double amount;
    private final LocalDateTime date;
    private final String description;
    private final UUID categoryId;
    private Operation createdOperation;

    public CreateOperationCommand(OperationFacade facade, CategoryType type, UUID bankAccountId,
                                  double amount, LocalDateTime date, String description, UUID categoryId) {
        this.facade = facade;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    @Override
    public void execute() {
        createdOperation = facade.createOperation(type, bankAccountId, amount, date, description, categoryId);
        System.out.println("Created operation: " + createdOperation.getType() + " - " + createdOperation.getAmount());
    }

    @Override
    public void undo() {
        if (createdOperation != null) {
            facade.deleteOperation(createdOperation.getId());
            System.out.println("Undo: Deleted operation: " + createdOperation.getType() + " - " + createdOperation.getAmount());
        }
    }
}