package com.hsebank.export;

import com.hsebank.domain.BankAccount;
import com.hsebank.domain.Category;
import com.hsebank.domain.Operation;

import java.util.Collection;
import java.util.stream.Collectors;

public final class JsonExportVisitor implements ExportVisitor {

    private static String q(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    @Override
    public String exportAccounts(Collection<BankAccount> accounts) {
        String body = accounts.stream()
                .map(a -> "{"
                        + "\"id\":" + q(a.getId()) + ","
                        + "\"name\":" + q(a.getName()) + ","
                        + "\"balance\":" + q(a.getBalance().toString())
                        + "}")
                .collect(Collectors.joining(","));
        return "[" + body + "]";
    }

    @Override
    public String exportCategories(Collection<Category> categories) {
        String body = categories.stream()
                .map(c -> "{"
                        + "\"id\":" + q(c.getId()) + ","
                        + "\"type\":" + q(c.getType().name()) + ","
                        + "\"name\":" + q(c.getName())
                        + "}")
                .collect(Collectors.joining(","));
        return "[" + body + "]";
    }

    @Override
    public String exportOperations(Collection<Operation> operations) {
        String body = operations.stream()
                .map(o -> "{"
                        + "\"id\":" + q(o.getId()) + ","
                        + "\"type\":" + q(o.getType().name()) + ","
                        + "\"bankAccountId\":" + q(o.getBankAccountId()) + ","
                        + "\"categoryId\":" + q(o.getCategoryId()) + ","
                        + "\"amount\":" + q(o.getAmount().toString()) + ","
                        + "\"date\":" + q(o.getDate().toString()) + ","
                        + "\"description\":" + (o.getDescription() == null ? "null" : q(o.getDescription()))
                        + "}")
                .collect(Collectors.joining(","));
        return "[" + body + "]";
    }
}
