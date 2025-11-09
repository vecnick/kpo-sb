package hse.finance.service;

import hse.finance.export.*;
import hse.finance.repository.BankAccountRepository;
import hse.finance.repository.CategoryRepository;
import hse.finance.repository.OperationRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExportService {
    private final BankAccountRepository accounts;
    private final CategoryRepository categories;
    private final OperationRepository operations;

    public ExportService(BankAccountRepository accounts, CategoryRepository categories, OperationRepository operations) {
        this.accounts = accounts;
        this.categories = categories;
        this.operations = operations;
    }

    public Path exportAll(String format, Path outDir, String baseName) throws IOException {
        ExportVisitor visitor = switch (format.toLowerCase()) {
            case "csv" -> new CSVExportVisitor();
            case "json" -> new JSONExportVisitor();
            case "yaml", "yml" -> new YAMLExportVisitor();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
        FinanceData data = new FinanceData(accounts.findAll(), categories.findAll(), operations.findAll());
        data.accept(visitor);
        String ext = visitor.getFileExtension();
        Files.createDirectories(outDir);
        Path file = outDir.resolve(baseName + "." + ext);
        Files.writeString(file, visitor.getResult(), StandardCharsets.UTF_8);
        return file;
    }
}

