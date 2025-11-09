package hse.finance.command.operations;

import hse.finance.command.Command;
import hse.finance.domain.OperationType;
import hse.finance.facade.OperationFacade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreateOperationCommand implements Command<Void> {
    private final OperationFacade facade;
    private final OperationType type;
    private final UUID accountId;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;
    private final UUID categoryId;

    public CreateOperationCommand(OperationFacade facade, OperationType type, UUID accountId, BigDecimal amount,
                                  LocalDate date, String description, UUID categoryId) {
        this.facade = facade;
        this.type = type;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    @Override
    public Void execute() {
        facade.create(type, accountId, amount, date, description, categoryId);
        System.out.println("Operation created.");
        return null;
    }

    @Override
    public String name() { return "CreateOperation"; }
}
