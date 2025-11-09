package hse.bank.command;

import hse.bank.io.importing.AbstractDataImporter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImportDataCommand implements Command {
    private final AbstractDataImporter importer;
    private final String filePath;

    @Override
    public void execute() {
        try {
            importer.importData(filePath);
            System.out.println("Data imported successfully from " + filePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to import data: " + e.getMessage(), e);
        }
    }
}