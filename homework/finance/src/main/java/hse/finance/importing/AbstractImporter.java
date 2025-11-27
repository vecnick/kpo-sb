package hse.finance.importing;

import hse.finance.domain.*;
import hse.finance.repository.BankAccountRepository;
import hse.finance.repository.CategoryRepository;
import hse.finance.repository.OperationRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// Template Method for importing data from a file
public abstract class AbstractImporter {
    protected final BankAccountRepository accounts;
    protected final CategoryRepository categories;
    protected final OperationRepository operations;
    protected final Factory factory;

    protected AbstractImporter(BankAccountRepository accounts,
                               CategoryRepository categories,
                               OperationRepository operations,
                               Factory factory) {
        this.accounts = accounts;
        this.categories = categories;
        this.operations = operations;
        this.factory = factory;
    }

    public void importFrom(Path file) throws IOException {
        String raw = Files.readString(file, StandardCharsets.UTF_8);
        ImportedData data = parse(raw);
        persist(data);
    }

    protected abstract ImportedData parse(String raw);

    protected void persist(ImportedData data) {
        for (ImportedData.AccountRow a : data.accounts()) {
            accounts.save(new BankAccount(a.id(), a.name(), a.balance()));
        }
        for (ImportedData.CategoryRow c : data.categories()) {
            categories.save(new Category(c.id(), c.type(), c.name()));
        }
        for (ImportedData.OperationRow o : data.operations()) {
            operations.save(new Operation(o.id(), o.type(), o.bankAccountId(), o.amount(), o.date(), o.description(), o.categoryId()));
        }
    }

    // simple parsed structures
    public record ImportedData(List<AccountRow> accounts, List<CategoryRow> categories, List<OperationRow> operations) {
        public record AccountRow(java.util.UUID id, String name, java.math.BigDecimal balance) {}
        public record CategoryRow(java.util.UUID id, CategoryType type, String name) {}
        public record OperationRow(java.util.UUID id, OperationType type, java.util.UUID bankAccountId,
                                   java.math.BigDecimal amount, java.time.LocalDate date, String description,
                                   java.util.UUID categoryId) {}
    }
}

