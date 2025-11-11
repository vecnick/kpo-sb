package hse_bank.finance.importexport;

import hse_bank.finance.domain.BankAccount;
import hse_bank.finance.domain.Category;
import hse_bank.finance.domain.Operation;

public interface DataVisitor {
    void visit(BankAccount account);
    void visit(Category category);
    void visit(Operation operation);
}