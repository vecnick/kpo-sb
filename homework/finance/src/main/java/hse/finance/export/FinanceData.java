package hse.finance.export;

import hse.finance.domain.BankAccount;
import hse.finance.domain.Category;
import hse.finance.domain.Operation;

import java.util.Collections;
import java.util.List;

public class FinanceData implements Visitable {
    private final List<BankAccount> accounts;
    private final List<Category> categories;
    private final List<Operation> operations;

    public FinanceData(List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
        this.accounts = List.copyOf(accounts);
        this.categories = List.copyOf(categories);
        this.operations = List.copyOf(operations);
    }

    public List<BankAccount> getAccounts() { return Collections.unmodifiableList(accounts); }
    public List<Category> getCategories() { return Collections.unmodifiableList(categories); }
    public List<Operation> getOperations() { return Collections.unmodifiableList(operations); }

    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }
}

