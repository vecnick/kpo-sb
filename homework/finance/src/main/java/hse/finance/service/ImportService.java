package hse.finance.service;

import hse.finance.domain.Factory;
import hse.finance.importing.AbstractImporter;
import hse.finance.importing.CSVImporter;
import hse.finance.repository.BankAccountRepository;
import hse.finance.repository.CategoryRepository;
import hse.finance.repository.OperationRepository;

import java.io.IOException;
import java.nio.file.Path;

public class ImportService {
    private final BankAccountRepository accounts;
    private final CategoryRepository categories;
    private final OperationRepository operations;
    private final Factory factory;

    public ImportService(BankAccountRepository accounts, CategoryRepository categories, OperationRepository operations, Factory factory) {
        this.accounts = accounts;
        this.categories = categories;
        this.operations = operations;
        this.factory = factory;
    }

    public void importFile(String format, Path file) throws IOException {
        AbstractImporter importer = switch (format.toLowerCase()) {
            case "csv" -> new CSVImporter(accounts, categories, operations, factory);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
        importer.importFrom(file);
    }
}

