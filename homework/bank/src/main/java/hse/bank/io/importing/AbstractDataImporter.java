package hse.bank.io.importing;

import hse.bank.domain.*;
import hse.bank.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class AbstractDataImporter {
    protected final BankAccountRepository bankAccountRepository;
    protected final CategoryRepository categoryRepository;
    protected final OperationRepository operationRepository;

    public AbstractDataImporter(BankAccountRepository bankAccountRepository,
                                CategoryRepository categoryRepository,
                                OperationRepository operationRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.categoryRepository = categoryRepository;
        this.operationRepository = operationRepository;
    }

    public final void importData(String filePath) throws IOException {
        String content = readFileContent(filePath);
        ParsedData data = parseContent(content);
        saveData(data);
    }

    protected String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    protected void saveData(ParsedData data) {
        bankAccountRepository.clear();
        categoryRepository.clear();
        operationRepository.clear();

        data.accounts.forEach(bankAccountRepository::save);
        data.categories.forEach(categoryRepository::save);
        data.operations.forEach(operationRepository::save);
    }

    protected abstract ParsedData parseContent(String content);

    protected static class ParsedData {
        final List<BankAccount> accounts;
        final List<Category> categories;
        final List<Operation> operations;

        public ParsedData(List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
            this.accounts = accounts;
            this.categories = categories;
            this.operations = operations;
        }
    }
}