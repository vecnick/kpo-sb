package hse.finance.export;

public interface ExportVisitor {
    void visit(FinanceData data);
    String getResult();
    String getFileExtension();
}

