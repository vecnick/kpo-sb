package hse.finance.service;

import hse.finance.domain.BankAccount;
import hse.finance.domain.Operation;
import hse.finance.domain.OperationType;
import hse.finance.repository.BankAccountRepository;
import hse.finance.repository.OperationRepository;

import java.math.BigDecimal;

public class BalanceService {
    private final BankAccountRepository accounts;
    private final OperationRepository operations;

    public BalanceService(BankAccountRepository accounts, OperationRepository operations) {
        this.accounts = accounts;
        this.operations = operations;
    }

    public void recalcAll() {
        for (BankAccount acc : accounts.findAll()) {
            BigDecimal income = operations.findByBankAccountId(acc.getId()).stream()
                    .filter(o -> o.getType() == OperationType.INCOME)
                    .map(Operation::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal expense = operations.findByBankAccountId(acc.getId()).stream()
                    .filter(o -> o.getType() == OperationType.EXPENSE)
                    .map(Operation::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            acc.setBalance(income.subtract(expense));
            accounts.save(acc);
        }
    }
}

