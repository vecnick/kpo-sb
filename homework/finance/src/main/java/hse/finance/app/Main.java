package hse.finance.app;

import hse.finance.command.TimeMeasuredCommandDecorator;
import hse.finance.command.accounts.CreateAccountCommand;
import hse.finance.command.accounts.ListAccountsCommand;
import hse.finance.command.analytics.IncomeMinusExpenseCommand;
import hse.finance.command.analytics.TotalByCategoryCommand;
import hse.finance.command.balance.RecalcBalanceCommand;
import hse.finance.command.categories.CreateCategoryCommand;
import hse.finance.command.categories.ListCategoriesCommand;
import hse.finance.command.exporting.ExportAllCommand;
import hse.finance.command.importing.ImportFileCommand;
import hse.finance.command.operations.CreateOperationCommand;
import hse.finance.command.operations.ListOperationsCommand;
import hse.finance.domain.Category;
import hse.finance.domain.CategoryType;
import hse.finance.domain.OperationType;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        AppContext ctx = new AppContext();

        if (args.length >= 2 && args[0].equalsIgnoreCase("import")) {
            String format = args.length >= 3 ? args[2] : "csv";
            var cmd = new TimeMeasuredCommandDecorator<>(
                    new ImportFileCommand(ctx.importService(), format, Path.of(args[1]))
            );
            cmd.execute();
            new TimeMeasuredCommandDecorator<>(new RecalcBalanceCommand(ctx.balance())).execute();
            new ListAccountsCommand(ctx.bankAccounts()).execute();
            return;
        }

        // Demo scenario
        System.out.println("=== Finance App Demo ===");
        var createIncomeCat = new TimeMeasuredCommandDecorator<>(
                new CreateCategoryCommand(ctx.categories(), CategoryType.INCOME, "Salary")
        );
        var createExpenseCat = new TimeMeasuredCommandDecorator<>(
                new CreateCategoryCommand(ctx.categories(), CategoryType.EXPENSE, "Cafe")
        );
        createIncomeCat.execute();
        createExpenseCat.execute();

        var accountCmd = new TimeMeasuredCommandDecorator<>(
                new CreateAccountCommand(ctx.bankAccounts(), "Main account", BigDecimal.ZERO)
        );
        accountCmd.execute();

        // Get created entities
        Category incomeCat = ctx.categories().list().stream().filter(c -> c.getType() == CategoryType.INCOME).findFirst().orElseThrow();
        Category expenseCat = ctx.categories().list().stream().filter(c -> c.getType() == CategoryType.EXPENSE).findFirst().orElseThrow();
        UUID accountId = ctx.bankAccounts().list().get(0).getId();

        var op1 = new TimeMeasuredCommandDecorator<>(
                new CreateOperationCommand(ctx.operations(), OperationType.INCOME, accountId,
                        new BigDecimal("100000"), LocalDate.now().minusDays(5), "Salary", incomeCat.getId())
        );
        var op2 = new TimeMeasuredCommandDecorator<>(
                new CreateOperationCommand(ctx.operations(), OperationType.EXPENSE, accountId,
                        new BigDecimal("1500"), LocalDate.now().minusDays(1), "Coffee", expenseCat.getId())
        );
        op1.execute();
        op2.execute();

        new TimeMeasuredCommandDecorator<>(new RecalcBalanceCommand(ctx.balance())).execute();

        new ListAccountsCommand(ctx.bankAccounts()).execute();
        new ListCategoriesCommand(ctx.categories()).execute();
        new ListOperationsCommand(ctx.operations()).execute();

        var ym = YearMonth.now();
        var from = ym.atDay(1);
        var to = ym.atEndOfMonth();
        new TimeMeasuredCommandDecorator<>(new IncomeMinusExpenseCommand(ctx.analytics(), from, to)).execute();
        new TimeMeasuredCommandDecorator<>(new TotalByCategoryCommand(ctx.analytics(), from, to)).execute();

        Path exportOutDir;
        Path cwd = Path.of(System.getProperty("user.dir"));
        if (cwd.getFileName() != null && cwd.getFileName().toString().equals("project")) {
            exportOutDir = cwd.resolve("build").resolve("exports");
        } else {
            exportOutDir = cwd.resolve("project").resolve("build").resolve("exports");
        }

        var exportCmd = new TimeMeasuredCommandDecorator<>(
                new ExportAllCommand(ctx.exportService(), "csv", exportOutDir, "finance-data")
        );
        exportCmd.execute();

        System.out.println("=== Demo complete ===");
    }
}
