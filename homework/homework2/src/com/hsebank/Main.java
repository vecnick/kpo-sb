package com.hsebank;

import com.hsebank.analytics.AnalyticsFacade;
import com.hsebank.command.*;
import com.hsebank.di.Container;
import com.hsebank.export.CsvExportVisitor;
import com.hsebank.export.JsonExportVisitor;
import com.hsebank.export.YamlExportVisitor;
import com.hsebank.factory.DomainFactory;
import com.hsebank.importer.CsvImporter;
import com.hsebank.importer.ImportFacade;
import com.hsebank.importer.JsonImporter;
import com.hsebank.importer.YamlImporter;
import com.hsebank.metrics.MetricsCollector;
import com.hsebank.repository.*;
import com.hsebank.service.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public final class Main {
    public static void main(String[] args) throws Exception {
        Container container = new Container();
        container.register(BankAccountRepository.class, InMemoryBankAccountRepository.class);
        container.register(CategoryRepository.class, InMemoryCategoryRepository.class);
        container.register(OperationRepository.class, InMemoryOperationRepository.class);
        container.register(DomainFactory.class, DomainFactory.class);

        BankAccountRepository bankRepo = container.resolve(BankAccountRepository.class);
        CategoryRepository categoryRepo = container.resolve(CategoryRepository.class);
        OperationRepository operationRepo = container.resolve(OperationRepository.class);
        DomainFactory factory = container.resolve(DomainFactory.class);

        BankAccountService bankService = new BankAccountService(bankRepo, factory);
        CategoryService categoryService = new CategoryService(categoryRepo, factory);
        OperationService operationService = new OperationService(operationRepo, bankRepo, factory);
        AnalyticsFacade analytics = new AnalyticsFacade(operationRepo);
        MetricsCollector metrics = new MetricsCollector();

        CsvImporter csvImporter = new CsvImporter();
        JsonImporter jsonImporter = new JsonImporter();
        YamlImporter yamlImporter = new YamlImporter();
        // <-- use services in ImportFacade so imports update balances and use service logic
        ImportFacade importFacade = new ImportFacade(bankService, categoryService, operationService);

        CsvExportVisitor csvExporter = new CsvExportVisitor();
        JsonExportVisitor jsonExporter = new JsonExportVisitor();
        YamlExportVisitor yamlExporter = new YamlExportVisitor();

        Scanner sc = new Scanner(System.in);

        loop:
        while (true) {
            System.out.println();
            System.out.println("menu:");
            System.out.println("1 - create account");
            System.out.println("2 - create category");
            System.out.println("3 - add operation");
            System.out.println("4 - list accounts");
            System.out.println("5 - list categories");
            System.out.println("6 - list operations");
            System.out.println("7 - analytics");
            System.out.println("8 - export (csv/json/yaml)");
            System.out.println("9 - import (csv/json/yaml)");
            System.out.println("10 - update account name");
            System.out.println("11 - delete account");
            System.out.println("12 - update category");
            System.out.println("13 - delete category");
            System.out.println("14 - update operation");
            System.out.println("15 - delete operation");
            System.out.println("0 - exit");
            String cmd = safeReadLine(sc, "choose:");
            if (cmd == null) break;
            switch (cmd.trim()) {
                case "0":
                    break loop;
                case "1":
                    execSafely(() -> {
                        String name = readNonEmpty(sc, "account name (or 'cancel'):");
                        if (name == null) return;
                        bankService.create(name);
                        System.out.println("created");
                    });
                    break;
                case "2":
                    execSafely(() -> {
                        String t = readEnumChoice(sc, "type INCOME/EXPENSE (or 'cancel'):", new String[]{"INCOME","EXPENSE"});
                        if (t == null) return;
                        String name = readNonEmpty(sc, "category name (or 'cancel'):");
                        if (name == null) return;
                        categoryService.create(com.hsebank.domain.Category.Type.valueOf(t), name);
                        System.out.println("created");
                    });
                    break;
                case "3":
                    execSafely(() -> {
                        String aid = chooseAccount(sc, bankRepo);
                        if (aid == null) return;
                        String cid = chooseCategory(sc, categoryRepo);
                        if (cid == null) return;
                        String t = readEnumChoice(sc, "type INCOME/EXPENSE (or 'cancel'):", new String[]{"INCOME","EXPENSE"});
                        if (t == null) return;
                        BigDecimal amount = readPositiveBigDecimal(sc, "amount (or 'cancel'):");
                        if (amount == null) return;
                        LocalDate date = readDate(sc, "date YYYY-MM-DD (or 'cancel'):");
                        if (date == null) return;
                        String desc = safeReadLine(sc, "description (optional):");
                        operationService.addOperation(com.hsebank.domain.Operation.Type.valueOf(t), aid, cid, amount, date, (desc==null||desc.isEmpty())?null:desc);
                        System.out.println("added");
                    });
                    break;
                case "4":
                    bankRepo.findAll().forEach(a -> System.out.println(a.getId()+" "+a.getName()+" "+a.getBalance()));
                    break;
                case "5":
                    categoryRepo.findAll().forEach(c -> System.out.println(c.getId()+" "+c.getType()+" "+c.getName()));
                    break;
                case "6":
                    operationRepo.findAll().forEach(o -> System.out.println(o.getId()+" "+o.getType()+" "+o.getAmount()+" "+o.getDate()+" "+(o.getDescription()==null?"":o.getDescription())));
                    break;
                case "7":
                    execSafely(() -> {
                        LocalDate from = readDate(sc, "from YYYY-MM-DD (or 'cancel'):");
                        if (from == null) return;
                        LocalDate to = readDate(sc, "to YYYY-MM-DD (or 'cancel'):");
                        if (to == null) return;
                        System.out.println("net: " + analytics.netForPeriod(from,to));
                        System.out.println("by category: " + analytics.groupByCategory(from,to));
                    });
                    break;
                case "8":
                    execSafely(() -> {
                        System.out.println("export target: 1-accounts 2-categories 3-operations");
                        String target = safeReadLine(sc, "> ");
                        if (target == null) return;
                        System.out.println("format: 1-csv 2-json 3-yaml");
                        String format = safeReadLine(sc, "> ");
                        if (format == null) return;
                        if ("1".equals(target)) {
                            if ("1".equals(format)) {
                                String fname = readNonEmpty(sc, "name for accounts csv (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, csvExporter.exportAccounts(bankRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else if ("2".equals(format)) {
                                String fname = readNonEmpty(sc, "name for accounts json (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, jsonExporter.exportAccounts(bankRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else if ("3".equals(format)) {
                                String fname = readNonEmpty(sc, "name for accounts yaml (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, yamlExporter.exportAccounts(bankRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else System.out.println("unknown format");
                        } else if ("2".equals(target)) {
                            if ("1".equals(format)) {
                                String fname = readNonEmpty(sc, "name for categories csv (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, csvExporter.exportCategories(categoryRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else if ("2".equals(format)) {
                                String fname = readNonEmpty(sc, "name for categories json (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, jsonExporter.exportCategories(categoryRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else if ("3".equals(format)) {
                                String fname = readNonEmpty(sc, "name for categories yaml (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, yamlExporter.exportCategories(categoryRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else System.out.println("unknown format");
                        } else if ("3".equals(target)) {
                            if ("1".equals(format)) {
                                String fname = readNonEmpty(sc, "name for operations csv (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, csvExporter.exportOperations(operationRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else if ("2".equals(format)) {
                                String fname = readNonEmpty(sc, "name for operations json (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, jsonExporter.exportOperations(operationRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else if ("3".equals(format)) {
                                String fname = readNonEmpty(sc, "name for operations yaml (or 'cancel')");
                                if (fname == null) return;
                                Path p = Paths.get(fname);
                                Files.write(p, yamlExporter.exportOperations(operationRepo.findAll()).getBytes(StandardCharsets.UTF_8));
                                System.out.println("wrote "+p.toAbsolutePath());
                            } else System.out.println("unknown format");
                        } else System.out.println("unknown target");
                    });
                    break;
                case "9":
                    execSafely(() -> {
                        System.out.println("import target: 1-accounts 2-categories 3-operations");
                        String target = safeReadLine(sc, "> ");
                        if (target == null) return;
                        System.out.println("format: 1-csv 2-json 3-yaml");
                        String format = safeReadLine(sc, "> ");
                        if (format == null) return;
                        String fname = readNonEmpty(sc, "name (or 'cancel')");
                        if (fname == null) return;
                        Path p = Paths.get(fname);
                        List<Map<String,Object>> data;
                        if ("1".equals(format)) data = (List) csvImporter.importAsListOfMaps(p);
                        else if ("2".equals(format)) data = jsonImporter.importAsListOfMaps(p);
                        else if ("3".equals(format)) data = yamlImporter.importAsListOfMaps(p);
                        else { System.out.println("unknown format"); return; }
                        if ("1".equals(target)) importFacade.importAccountsFromMaps(data);
                        else if ("2".equals(target)) importFacade.importCategoriesFromMaps(data);
                        else if ("3".equals(target)) importFacade.importOperationsFromMaps(data);
                        else { System.out.println("unknown target"); return; }
                        System.out.println("imported from "+p.toAbsolutePath());
                    });
                    break;
                case "10":
                    execSafely(() -> {
                        String id = chooseAccount(sc, bankRepo);
                        if (id == null) return;
                        String newName = readNonEmpty(sc, "new name (or 'cancel'):");
                        if (newName == null) return;
                        bankService.updateAccountName(id, newName);
                        System.out.println("updated");
                    });
                    break;
                case "11":
                    execSafely(() -> {
                        String id = chooseAccount(sc, bankRepo);
                        if (id == null) return;
                        bankService.delete(id);
                        System.out.println("deleted");
                    });
                    break;
                case "12":
                    execSafely(() -> {
                        String id = chooseCategory(sc, categoryRepo);
                        if (id == null) return;
                        String t = readEnumChoice(sc, "type INCOME/EXPENSE (or 'cancel'):", new String[]{"INCOME","EXPENSE"});
                        if (t == null) return;
                        String newName = readNonEmpty(sc, "new name (or 'cancel'):");
                        if (newName == null) return;
                        categoryService.updateCategory(id, com.hsebank.domain.Category.Type.valueOf(t), newName);
                        System.out.println("updated");
                    });
                    break;
                case "13":
                    execSafely(() -> {
                        String id = chooseCategory(sc, categoryRepo);
                        if (id == null) return;
                        categoryService.delete(id);
                        System.out.println("deleted");
                    });
                    break;
                case "14":
                    execSafely(() -> {
                        String id = readNonEmpty(sc, "operation id (or 'cancel'):");
                        if (id == null) return;
                        Optional<com.hsebank.domain.Operation> oOpt = operationService.find(id);
                        if (!oOpt.isPresent()) { System.out.println("operation not found"); return; }
                        String accountId = chooseAccount(sc, bankRepo);
                        if (accountId == null) return;
                        String categoryId = chooseCategory(sc, categoryRepo);
                        if (categoryId == null) return;
                        String t = readEnumChoice(sc, "type INCOME/EXPENSE (or 'cancel'):", new String[]{"INCOME","EXPENSE"});
                        if (t == null) return;
                        BigDecimal amount = readPositiveBigDecimal(sc, "amount (or 'cancel'):");
                        if (amount == null) return;
                        LocalDate date = readDate(sc, "date YYYY-MM-DD (or 'cancel'):");
                        if (date == null) return;
                        String desc = safeReadLine(sc, "description (optional):");
                        operationService.updateOperation(id, com.hsebank.domain.Operation.Type.valueOf(t), accountId, categoryId, amount, date, (desc==null||desc.isEmpty())?null:desc);
                        System.out.println("updated");
                    });
                    break;
                case "15":
                    execSafely(() -> {
                        String id = readNonEmpty(sc, "operation id (or 'cancel'):");
                        if (id == null) return;
                        operationService.delete(id);
                        System.out.println("deleted");
                    });
                    break;

                default:
                    System.out.println("unknown command");
            }
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private static void execSafely(ThrowingRunnable r) {
        try {
            r.run();
        } catch (Exception e) {
            System.out.println("error: " + e.getClass().getSimpleName()+ " - " + e.getMessage());
        }
    }

    private static String safeReadLine(Scanner sc, String prompt) {
        System.out.print(prompt+" ");
        try {
            if (!sc.hasNextLine()) return null;
            return sc.nextLine();
        } catch (Exception e) {
            return null;
        }
    }

    private static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            String s = safeReadLine(sc, prompt);
            if (s == null) return null;
            s = s.trim();
            if ("cancel".equalsIgnoreCase(s)) return null;
            if (!s.isEmpty()) return s;
            System.out.println("empty input, try again or 'cancel'");
        }
    }

    private static String readEnumChoice(Scanner sc, String prompt, String[] opts) {
        while (true) {
            String s = safeReadLine(sc, prompt);
            if (s == null) return null;
            if ("cancel".equalsIgnoreCase(s)) return null;
            for (String o : opts) if (o.equalsIgnoreCase(s.trim())) return o;
            System.out.println("invalid value, allowed: " + String.join("/", opts) + " or 'cancel'");
        }
    }

    private static BigDecimal readPositiveBigDecimal(Scanner sc, String prompt) {
        while (true) {
            String s = safeReadLine(sc, prompt);
            if (s == null) return null;
            if ("cancel".equalsIgnoreCase(s)) return null;
            try {
                BigDecimal v = new BigDecimal(s.trim());
                if (v.compareTo(BigDecimal.ZERO) <= 0) { System.out.println("must be positive"); continue; }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("invalid number");
            }
        }
    }

    private static LocalDate readDate(Scanner sc, String prompt) {
        while (true) {
            String s = safeReadLine(sc, prompt);
            if (s == null) return null;
            if ("cancel".equalsIgnoreCase(s)) return null;
            try {
                return LocalDate.parse(s.trim());
            } catch (DateTimeParseException e) {
                System.out.println("invalid date format YYYY-MM-DD");
            }
        }
    }

    private static String chooseAccount(Scanner sc, BankAccountRepository repo) {
        repo.findAll().forEach(a -> System.out.println(a.getId()+" "+a.getName()+" "+a.getBalance()));
        return readNonEmpty(sc, "accountId (or 'cancel'):");
    }

    private static String chooseCategory(Scanner sc, CategoryRepository repo) {
        repo.findAll().forEach(c -> System.out.println(c.getId()+" "+c.getType()+" "+c.getName()));
        return readNonEmpty(sc, "categoryId (or 'cancel'):");
    }
}
