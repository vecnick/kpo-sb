package hse.finance.export;

import hse.finance.domain.BankAccount;
import hse.finance.domain.Category;
import hse.finance.domain.Operation;

import java.util.stream.Collectors;

public class CSVExportVisitor implements ExportVisitor {
    private String result;
    private static final char SEP = ';';

    @Override
    public void visit(FinanceData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("#ACCOUNTS\n");
        sb.append("id" + SEP + "name" + SEP + "balance\n");
        for (BankAccount a : data.getAccounts()) {
            sb.append(a.getId()).append(SEP)
              .append(escape(a.getName())).append(SEP)
              .append(a.getBalance()).append('\n');
        }
        sb.append("#CATEGORIES\n");
        sb.append("id" + SEP + "type" + SEP + "name\n");
        for (Category c : data.getCategories()) {
            sb.append(c.getId()).append(SEP)
              .append(c.getType()).append(SEP)
              .append(escape(c.getName())).append('\n');
        }
        sb.append("#OPERATIONS\n");
        sb.append("id" + SEP + "type" + SEP + "bank_account_id" + SEP + "amount" + SEP + "date" + SEP + "description" + SEP + "category_id\n");
        for (Operation o : data.getOperations()) {
            sb.append(o.getId()).append(SEP)
              .append(o.getType()).append(SEP)
              .append(o.getBankAccountId()).append(SEP)
              .append(o.getAmount()).append(SEP)
              .append(o.getDate()).append(SEP)
              .append(escape(o.getDescription())).append(SEP)
              .append(o.getCategoryId()).append('\n');
        }
        result = sb.toString();
    }

    private String escape(String s) {
        if (s == null) return "";
        String str = s;
        if (str.indexOf('"') >= 0) {
            str = str.replace("\"", "\"\"");
        }
        if (str.indexOf(SEP) >= 0 || str.indexOf('\n') >= 0 || s.indexOf('"') >= 0) {
            return '"' + str + '"';
        }
        return str;
    }

    @Override
    public String getResult() { return result == null ? "" : result; }

    @Override
    public String getFileExtension() { return "csv"; }
}
