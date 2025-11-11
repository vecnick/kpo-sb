package hse_bank.finance.importexport;

import hse_bank.finance.domain.BankAccount;
import hse_bank.finance.domain.Category;
import hse_bank.finance.domain.Operation;

import java.util.List;

public abstract class DataImporter {
    // Template Method
    public final void importData(String filePath) {
        String content = readFile(filePath);
        List<?> data = parseData(content);
        validateData(data);
        saveData(data);
        System.out.println("Data imported successfully from: " + filePath);
    }

    protected abstract String readFile(String filePath);
    protected abstract List<?> parseData(String content);

    protected void validateData(List<?> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("No data to import");
        }
    }

    protected abstract void saveData(List<?> data);
}