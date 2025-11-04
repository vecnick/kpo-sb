package hse.finance.command.exporting;

import hse.finance.command.Command;
import hse.finance.service.ExportService;

import java.io.IOException;
import java.nio.file.Path;

public class ExportAllCommand implements Command<Path> {
    private final ExportService exportService;
    private final String format;
    private final Path outDir;
    private final String baseName;

    public ExportAllCommand(ExportService exportService, String format, Path outDir, String baseName) {
        this.exportService = exportService;
        this.format = format;
        this.outDir = outDir;
        this.baseName = baseName;
    }

    @Override
    public Path execute() {
        try {
            Path file = exportService.exportAll(format, outDir, baseName);
            System.out.println("Export completed: " + file);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String name() { return "ExportAll(" + format + ")"; }
}
