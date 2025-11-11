package hse_bank.finance.importexport;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse_bank.finance.domain.BankAccount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonDataImporter extends DataImporter {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected String readFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }

    @Override
    protected List<?> parseData(String content) {
        try {
            // Simplified - in real implementation would handle different types
            return mapper.readValue(content,
                    mapper.getTypeFactory().constructCollectionType(List.class, BankAccount.class));
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    @Override
    protected void saveData(List<?> data) {
        // Implementation would save to repositories
        System.out.println("Saving " + data.size() + " items from JSON");
    }
}