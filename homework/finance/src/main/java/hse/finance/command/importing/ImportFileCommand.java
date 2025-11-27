package hse.finance.command.importing;

import hse.finance.command.Command;
import hse.finance.service.ImportService;

import java.nio.file.Path;

public class ImportFileCommand implements Command<Void> {
    private final ImportService importService;
    private final String format;
    private final Path file;

    public ImportFileCommand(ImportService importService, String format, Path file) {
        this.importService = importService;
        this.format = format;
        this.file = file;
    }

    @Override
    public Void execute() {
        try {
            importService.importFile(format, file);
            System.out.println("Import completed: " + file);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String name() { return "Import(" + format + ")"; }
}
