package com.hsebank.command;

import com.hsebank.service.BankAccountService;

public final class CreateAccountCommand implements Command {
    private final BankAccountService svc;
    private final String name;


    public CreateAccountCommand(BankAccountService svc, String name) {
        this.svc = svc;
        this.name = name;
    }

    public void execute() {
        svc.create(name);
    }
}