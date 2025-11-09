package hse.bank.io.export;

import hse.bank.domain.*;

public interface DataVisitor {
    void visit(BankAccount account);
    void visit(Category category);
    void visit(Operation operation);
    String getResult();
}