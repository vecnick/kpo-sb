package hse_bank;

import hse_bank.finance.commands.Command;
import hse_bank.finance.commands.CreateBankAccountCommand;
import hse_bank.finance.commands.CreateOperationCommand;
import hse_bank.finance.commands.TimingDecorator;
import hse_bank.finance.config.AppConfig;
import hse_bank.finance.domain.BankAccount;
import hse_bank.finance.domain.CategoryType;
import hse_bank.finance.facade.AnalyticsFacade;
import hse_bank.finance.facade.BankAccountFacade;
import hse_bank.finance.facade.CategoryFacade;
import hse_bank.finance.facade.OperationFacade;

import java.time.LocalDateTime;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();

        BankAccountFacade bankAccountFacade = config.bankAccountFacade();
        CategoryFacade categoryFacade = config.categoryFacade();
        OperationFacade operationFacade = config.operationFacade();
        AnalyticsFacade analyticsFacade = config.analyticsFacade();

        System.out.println("=== HSE Bank Finance Module Demo ===");

        try {
            // 1. Создаем счет напрямую через фасад (без Command для надежности)
            System.out.println("\n1. Creating bank account:");
            BankAccount account = bankAccountFacade.createAccount("Main Account", 1000.0);
            UUID accountId = account.getId();
            System.out.println("Created account: " + account.getName() + " with ID: " + accountId);
            System.out.println("Initial balance: " + account.getBalance());

            // 2. Создаем категории
            System.out.println("\n2. Creating categories:");
            var salaryCategory = categoryFacade.createCategory(CategoryType.INCOME, "Salary");
            var foodCategory = categoryFacade.createCategory(CategoryType.EXPENSE, "Food");
            var entertainmentCategory = categoryFacade.createCategory(CategoryType.EXPENSE, "Entertainment");

            System.out.println("Created categories: Salary, Food, Entertainment");

            // 3. Создаем операции через Command pattern
            System.out.println("\n3. Creating operations with Command pattern:");

            Command createIncomeCommand = new CreateOperationCommand(
                    operationFacade, CategoryType.INCOME, accountId, 2000.0,
                    LocalDateTime.now().minusDays(5), "Monthly salary", salaryCategory.getId()
            );

            Command createExpenseCommand = new CreateOperationCommand(
                    operationFacade, CategoryType.EXPENSE, accountId, 150.0,
                    LocalDateTime.now().minusDays(2), "Lunch at cafe", foodCategory.getId()
            );

            Command createExpenseCommand2 = new CreateOperationCommand(
                    operationFacade, CategoryType.EXPENSE, accountId, 300.0,
                    LocalDateTime.now().minusDays(1), "Cinema", entertainmentCategory.getId()
            );

            // Выполняем команды с измерением времени
            Command timedIncomeCommand = new TimingDecorator(createIncomeCommand);
            Command timedExpenseCommand = new TimingDecorator(createExpenseCommand);
            Command timedExpenseCommand2 = new TimingDecorator(createExpenseCommand2);

            timedIncomeCommand.execute();
            timedExpenseCommand.execute();
            timedExpenseCommand2.execute();

            // 4. Демонстрация аналитики
            System.out.println("\n4. Analytics demonstration:");

            double totalIncome = analyticsFacade.getTotalIncome();
            double totalExpense = analyticsFacade.getTotalExpense();
            double currentBalance = analyticsFacade.getCurrentBalance();

            System.out.println("Total income: " + totalIncome);
            System.out.println("Total expense: " + totalExpense);
            System.out.println("Current balance: " + currentBalance);

            // 5. Показываем итоговый баланс счета
            var updatedAccount = bankAccountFacade.getAccount(accountId);
            updatedAccount.ifPresent(acc -> {
                System.out.println("\n5. Updated account details:");
                System.out.println("Account name: " + acc.getName());
                System.out.println("Account balance: " + acc.getBalance());
            });

            // 6. Демонстрация операций по счету
            var accountOperations = operationFacade.getOperationsByAccount(accountId);
            System.out.println("\n6. Operations for account (" + accountOperations.size() + " operations):");
            accountOperations.forEach(op -> {
                System.out.println(" - " + op.getType() + ": " + op.getAmount() + " (" + op.getDescription() + ")");
            });

            // 7. Демонстрация undo
            System.out.println("\n7. Demonstrating undo for last operation:");
            timedExpenseCommand2.undo();

            // 8. Финальный баланс
            var finalAccount = bankAccountFacade.getAccount(accountId);
            finalAccount.ifPresent(acc -> {
                System.out.println("\n8. Final account balance after undo: " + acc.getBalance());
            });

        } catch (Exception e) {
            System.err.println("Error during demo: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== Demo completed! ===");
    }
}