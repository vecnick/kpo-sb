package hse.finance.export;

import hse.finance.domain.BankAccount;
import hse.finance.domain.Category;
import hse.finance.domain.Operation;

import java.util.stream.Collectors;

public class JSONExportVisitor implements ExportVisitor {
    private String result;

    @Override
    public void visit(FinanceData data) {
        String accounts = data.getAccounts().stream().map(this::accToJson).collect(Collectors.joining(","));
        String categories = data.getCategories().stream().map(this::catToJson).collect(Collectors.joining(","));
        String operations = data.getOperations().stream().map(this::opToJson).collect(Collectors.joining(","));
        result = "{" +
                "\"accounts\":[" + accounts + "]," +
                "\"categories\":[" + categories + "]," +
                "\"operations\":[" + operations + "]" +
                "}";
    }

    private String esc(String s) { return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\""); }

    private String accToJson(BankAccount a) {
        return "{" +
                "\"id\":\"" + a.getId() + "\"," +
                "\"name\":\"" + esc(a.getName()) + "\"," +
                "\"balance\":" + a.getBalance() +
                "}";
    }

    private String catToJson(Category c) {
        return "{" +
                "\"id\":\"" + c.getId() + "\"," +
                "\"type\":\"" + c.getType() + "\"," +
                "\"name\":\"" + esc(c.getName()) + "\"" +
                "}";
    }

    private String opToJson(Operation o) {
        return "{" +
                "\"id\":\"" + o.getId() + "\"," +
                "\"type\":\"" + o.getType() + "\"," +
                "\"bank_account_id\":\"" + o.getBankAccountId() + "\"," +
                "\"amount\":" + o.getAmount() + "," +
                "\"date\":\"" + o.getDate() + "\"," +
                "\"description\":\"" + esc(o.getDescription()) + "\"," +
                "\"category_id\":\"" + o.getCategoryId() + "\"" +
                "}";
    }

    @Override
    public String getResult() { return result == null ? "{}" : result; }

    @Override
    public String getFileExtension() { return "json"; }
}

