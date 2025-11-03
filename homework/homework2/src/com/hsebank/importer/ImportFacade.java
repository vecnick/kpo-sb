package com.hsebank.importer;

import com.hsebank.service.BankAccountService;
import com.hsebank.service.CategoryService;
import com.hsebank.service.OperationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ImportFacade {
    private final BankAccountService bankService;
    private final CategoryService categoryService;
    private final OperationService operationService;

    public ImportFacade(BankAccountService bankService, CategoryService categoryService, OperationService operationService) {
        this.bankService = bankService;
        this.categoryService = categoryService;
        this.operationService = operationService;
    }

    public void importAccountsFromMaps(List<Map<String, Object>> maps) {
        for (Map<String, Object> m : maps) {
            try {
                String id = getString(m, "id", "Id", "ID");
                String name = getString(m, "name", "Name");
                String bal = getString(m, "balance", "Balance");

                BigDecimal balance = BigDecimal.ZERO;
                if (bal != null && !bal.trim().isEmpty()) balance = new BigDecimal(bal.trim());

                bankService.createOrUpdateAccount(id, name, balance);
            } catch (Exception e) {
                System.out.println("importAccounts: skipping record due to error: " + e.getMessage());
            }
        }
    }

    public void importCategoriesFromMaps(List<Map<String, Object>> maps) {
        for (Map<String, Object> m : maps) {
            try {
                String id = getString(m, "id", "Id", "ID");
                String name = getString(m, "name", "Name");
                String type = getString(m, "type", "Type");

                if (type == null) throw new IllegalArgumentException("category type missing");
                categoryService.createOrUpdate(id, com.hsebank.domain.Category.Type.valueOf(type.trim()), name);
                // update name separately to ensure both fields applied
                if (name != null) categoryService.updateCategory(id != null ? id : findCategoryIdByName(name), com.hsebank.domain.Category.Type.valueOf(type.trim()), name);
            } catch (Exception e) {
                System.out.println("importCategories: skipping record due to error: " + e.getMessage());
            }
        }
    }

    public void importOperationsFromMaps(List<Map<String, Object>> maps) {
        for (Map<String, Object> m : maps) {
            try {
                String type = getString(m, "type", "Type");
                String accountId = getString(m, "bankAccountId", "bankaccountid", "accountId", "accountid", "account");
                String categoryId = getString(m, "categoryId", "categoryid", "category");
                String amount = getString(m, "amount", "Amount");
                String date = getString(m, "date", "Date");
                String desc = getString(m, "description", "Description");

                if (type == null) throw new IllegalArgumentException("operation type missing");
                if (accountId == null) throw new IllegalArgumentException("operation accountId missing");
                if (categoryId == null) throw new IllegalArgumentException("operation categoryId missing");
                if (amount == null) throw new IllegalArgumentException("operation amount missing");
                if (date == null) throw new IllegalArgumentException("operation date missing");

                com.hsebank.domain.Operation.Type t = com.hsebank.domain.Operation.Type.valueOf(type.trim());
                BigDecimal amt = new BigDecimal(amount.trim());
                LocalDate d = LocalDate.parse(date.trim());

                operationService.addOperation(t, accountId.trim(), categoryId.trim(), amt, d, (desc == null || desc.trim().isEmpty()) ? null : desc.trim());
            } catch (Exception e) {
                System.out.println("importOperations: skipping record due to error: " + e.getMessage());
            }
        }
    }

    private static String getString(Map<String, Object> m, String... keys) {
        if (m == null) return null;
        for (String k : keys) {
            for (Map.Entry<String, Object> e : m.entrySet()) {
                if (e.getKey() == null) continue;
                if (e.getKey().equalsIgnoreCase(k)) {
                    Object v = e.getValue();
                    return v == null ? null : Objects.toString(v);
                }
            }
        }
        return null;
    }

    private String findCategoryIdByName(String name) {
        return categoryService.list().stream()
                .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(name))
                .map(c -> c.getId())
                .findFirst().orElse(null);
    }
}
