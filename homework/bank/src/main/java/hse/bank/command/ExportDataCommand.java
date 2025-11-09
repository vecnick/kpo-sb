package hse.bank.command;

import hse.bank.domain.*;
import hse.bank.io.export.DataVisitor;
import hse.bank.repository.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExportDataCommand implements Command {
    private final BankAccountRepository bankAccountRepository;
    private final CategoryRepository categoryRepository;
    private final OperationRepository operationRepository;
    private final DataVisitor visitor;
    private final String filePath;

    @Override
    public void execute() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        List<Operation> operations = operationRepository.findAll();

        accounts.forEach(account -> account.accept(visitor));
        categories.forEach(category -> category.accept(visitor));
        operations.forEach(operation -> operation.accept(visitor));

        String result = visitor.getResult();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(result);
            System.out.println("Data exported successfully to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data: " + e.getMessage(), e);
        }
    }
}