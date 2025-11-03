package com.hsebank.command;

import com.hsebank.domain.Operation;
import com.hsebank.service.OperationService;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class AddOperationCommand implements Command {
    private final OperationService svc;
    private final Operation.Type type;
    private final String accountId;
    private final String categoryId;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;

    public AddOperationCommand(OperationService svc, Operation.Type type, String accountId, String categoryId, BigDecimal amount, LocalDate date, String description) {
        this.svc = svc;
        this.type = type;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public void execute() {
        svc.create(type, accountId, categoryId, amount, date, description);
    }
}