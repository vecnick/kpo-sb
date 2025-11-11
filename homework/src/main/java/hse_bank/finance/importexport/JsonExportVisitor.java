package hse_bank.finance.importexport;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse_bank.finance.domain.BankAccount;
import hse_bank.finance.domain.Category;
import hse_bank.finance.domain.Operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonExportVisitor implements DataVisitor {
    private final List<Object> data = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void visit(BankAccount account) {
        data.add(account);
    }

    @Override
    public void visit(Category category) {
        data.add(category);
    }

    @Override
    public void visit(Operation operation) {
        data.add(operation);
    }

    public String export() {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (IOException e) {
            throw new RuntimeException("Error exporting to JSON", e);
        }
    }
}