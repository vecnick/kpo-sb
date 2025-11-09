package hse.finance.export;

import hse.finance.domain.BankAccount;
import hse.finance.domain.Category;
import hse.finance.domain.Operation;

public class YAMLExportVisitor implements ExportVisitor {
    private String result;

    @Override
    public void visit(FinanceData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("accounts:\n");
        for (BankAccount a : data.getAccounts()) {
            sb.append("  - id: ").append(a.getId()).append('\n');
            sb.append("    name: \"").append(escape(a.getName())).append("\"\n");
            sb.append("    balance: ").append(a.getBalance()).append('\n');
        }
        sb.append("categories:\n");
        for (Category c : data.getCategories()) {
            sb.append("  - id: ").append(c.getId()).append('\n');
            sb.append("    type: ").append(c.getType()).append('\n');
            sb.append("    name: \"").append(escape(c.getName())).append("\"\n");
        }
        sb.append("operations:\n");
        for (Operation o : data.getOperations()) {
            sb.append("  - id: ").append(o.getId()).append('\n');
            sb.append("    type: ").append(o.getType()).append('\n');
            sb.append("    bank_account_id: ").append(o.getBankAccountId()).append('\n');
            sb.append("    amount: ").append(o.getAmount()).append('\n');
            sb.append("    date: \"").append(o.getDate()).append("\"\n");
            sb.append("    description: \"").append(escape(o.getDescription())).append("\"\n");
            sb.append("    category_id: ").append(o.getCategoryId()).append('\n');
        }
        result = sb.toString();
    }

    private String escape(String s) { return s == null ? "" : s.replace("\"","\\\""); }

    @Override
    public String getResult() { return result == null ? "" : result; }

    @Override
    public String getFileExtension() { return "yaml"; }
}

