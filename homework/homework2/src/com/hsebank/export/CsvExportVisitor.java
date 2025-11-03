package com.hsebank.export;


import com.hsebank.domain.BankAccount;
import com.hsebank.domain.Category;
import com.hsebank.domain.Operation;


import java.util.Collection;
import java.util.stream.Collectors;


public final class CsvExportVisitor implements ExportVisitor {
    public String exportAccounts(Collection<BankAccount> accounts) {
        String header = "id,name,balance";
        return header + "\n" + accounts.stream().map(a -> String.join(",", a.getId(), a.getName(), a.getBalance().toString())).collect(Collectors.joining("\n"));
    }


    public String exportCategories(Collection<Category> categories) {
        String header = "id,type,name";
        return header + "\n" + categories.stream().map(c -> String.join(",", c.getId(), c.getType().name(), c.getName())).collect(Collectors.joining("\n"));
    }


    public String exportOperations(Collection<Operation> operations) {
        String header = "id,type,accountId,categoryId,amount,date,description";
        return header + "\n" + operations.stream().map(o -> String.join(",", o.getId(), o.getType().name(), o.getBankAccountId(), o.getCategoryId(), o.getAmount().toString(), o.getDate().toString(), o.getDescription() == null ? "" : o.getDescription())).collect(Collectors.joining("\n"));
    }
}