package hse.finance.importing;

import hse.finance.domain.CategoryType;
import hse.finance.domain.OperationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CSVImporter extends AbstractImporter {
    public CSVImporter(hse.finance.repository.BankAccountRepository accounts,
                       hse.finance.repository.CategoryRepository categories,
                       hse.finance.repository.OperationRepository operations,
                       hse.finance.domain.Factory factory) {
        super(accounts, categories, operations, factory);
    }

    @Override
    protected ImportedData parse(String raw) {
        List<ImportedData.AccountRow> accs = new ArrayList<>();
        List<ImportedData.CategoryRow> cats = new ArrayList<>();
        List<ImportedData.OperationRow> ops = new ArrayList<>();

        String section = "";
        String[] lines = raw.split("\r?\n");
        for (String line : lines) {
            if (line.isBlank()) continue;
            if (line.startsWith("#")) {
                section = line.trim();
                continue;
            }
            if (line.startsWith("id,")) { // header
                continue;
            }
            String[] cols = splitCsv(line);
            switch (section) {
                case "#ACCOUNTS" -> {
                    UUID id = UUID.fromString(cols[0]);
                    String name = unquote(cols[1]);
                    BigDecimal balance = new BigDecimal(cols[2]);
                    accs.add(new ImportedData.AccountRow(id, name, balance));
                }
                case "#CATEGORIES" -> {
                    UUID id = UUID.fromString(cols[0]);
                    CategoryType type = CategoryType.valueOf(cols[1]);
                    String name = unquote(cols[2]);
                    cats.add(new ImportedData.CategoryRow(id, type, name));
                }
                case "#OPERATIONS" -> {
                    UUID id = UUID.fromString(cols[0]);
                    OperationType type = OperationType.valueOf(cols[1]);
                    UUID accountId = UUID.fromString(cols[2]);
                    BigDecimal amount = new BigDecimal(cols[3]);
                    LocalDate date = LocalDate.parse(cols[4]);
                    String description = cols.length > 5 ? unquote(cols[5]) : null;
                    UUID categoryId = UUID.fromString(cols[6]);
                    ops.add(new ImportedData.OperationRow(id, type, accountId, amount, date, description, categoryId));
                }
                default -> { /* ignore */ }
            }
        }
        return new ImportedData(accs, cats, ops);
    }

    private String unquote(String s) {
        s = s == null ? "" : s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1).replace("\\\"", "\"");
        }
        return s;
    }

    // naive CSV split supporting double-quoted values with commas
    private String[] splitCsv(String line) {
        List<String> res = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++; // skip escaped quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                res.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        res.add(cur.toString());
        return res.toArray(new String[0]);
    }
}

