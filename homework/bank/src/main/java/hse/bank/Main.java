package hse.bank;

import hse.bank.command.*;
import hse.bank.config.ApplicationContext;
import hse.bank.decorator.TimingCommandDecorator;
import hse.bank.enums.OperationType;
import hse.bank.dto.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ApplicationContext context = new ApplicationContext();

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to HSE-Bank (CLI)");
        runMainMenu();
        System.out.println("Goodbye!");
    }

    private static void runMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Delete Account");
            System.out.println("3. Create Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Add Operation (Income/Expense)");
            System.out.println("6. Delete Operation");
            System.out.println("7. Show Analytics (Income - Expense)");
            System.out.println("8. Export Data to JSON");
            System.out.println("9. Import Data from JSON");
            System.out.println("0. Exit");
            System.out.println("Choose an option:");

            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        handleCreateAccount();
                        break;
                    case 2:
                        handleDeleteAccount();
                        break;
                    case 3:
                        handleCreateCategory();
                        break;
                    case 4:
                        handleDeleteCategory();
                        break;
                    case 5:
                        handleAddOperation();
                        break;
                    case 6:
                        handleDeleteOperation();
                        break;
                    case 7:
                        handleGetAnalytics();
                        break;
                    case 8:
                        handleExport();
                        break;
                    case 9:
                        handleImport();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void handleCreateAccount() {
        System.out.print("Enter account name:");
        String name = scanner.nextLine();
        CreateAccountRequest request = new CreateAccountRequest(name);

        Command command = context.getCreateAccountCommand(request);
        executeDecorated(command);
    }

    private static void handleDeleteAccount() {
        System.out.print("Enter account id:");
        Long id = Long.parseLong(scanner.nextLine().trim());
        DeleteAccountRequest request = new DeleteAccountRequest(id);

        Command command = context.getDeleteAccountCommand(request);
        executeDecorated(command);
    }

    private static void handleCreateCategory() {
        System.out.print("Enter category name:");
        String name = scanner.nextLine();
        System.out.print("Enter type (INCOME or EXPENSE):");
        OperationType type = OperationType.valueOf(scanner.nextLine().toUpperCase());
        CreateCategoryRequest request = new CreateCategoryRequest(name, type);

        Command command = context.getCreateCategoryCommand(request);
        executeDecorated(command);
    }

    private static void handleDeleteCategory() {
        System.out.print("Enter category id:");
        Long id = Long.parseLong(scanner.nextLine().trim());
        DeleteCategoryRequest request = new DeleteCategoryRequest(id);

        Command command = context.getDeleteCategoryCommand(request);
        executeDecorated(command);
    }

    private static void handleAddOperation() {
        try {
            System.out.print("Enter account ID:");
            long accountId = Long.parseLong(scanner.nextLine());
            System.out.print("Enter category ID:");
            long categoryId = Long.parseLong(scanner.nextLine());
            System.out.print("Enter type (INCOME or EXPENSE):");
            OperationType type = OperationType.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter the amount (e.g. 100.50):");
            BigDecimal amount = new BigDecimal(scanner.nextLine());
            System.out.print("Enter the date (YYYY-MM-DD):");
            LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            System.out.print("Enter a description (optional):");
            String description = scanner.nextLine();

            CreateOperationRequest request = new CreateOperationRequest(type, amount, date, description, accountId, categoryId);

            Command command = context.getAddOperationCommand(request);
            executeDecorated(command);

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid type. Enter INCOME or EXPENSE.");
        }
    }

    private static void handleDeleteOperation() {
        System.out.print("Enter operation id:");
        Long id = Long.parseLong(scanner.nextLine().trim());
        DeleteOperationRequest request = new DeleteOperationRequest(id);

        Command command = context.getDeleteOperationCommand(request);
        executeDecorated(command);
    }

    private static void handleGetAnalytics() {
        try {
            System.out.print("Enter start date (YYYY-MM-DD):");
            LocalDate from = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            System.out.print("Enter end date (YYYY-MM-DD):");
            LocalDate to = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);

            Command command = context.getAnalyticsCommand(from, to);
            executeDecorated(command);

        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private static void handleExport() {
        System.out.print("Enter the path to the file to export (e.g. export.json):");
        String path = scanner.nextLine();
        Command command = context.getExportDataCommand(path);
        executeDecorated(command);
    }

    private static void handleImport() {
        System.out.print("Enter the path to the file to import (e.g. import.json):");
        String path = scanner.nextLine();
        Command command = context.getImportDataCommand(path);
        executeDecorated(command);
    }

    private static void executeDecorated(Command command) {
        System.out.println("\n--- Executing the script ---");
        Command decoratedCommand = new TimingCommandDecorator(command);
        try {
            decoratedCommand.execute();
            System.out.println("--- Script completed ---");
        } catch (Exception e) {
            System.err.println("!!! Runtime error:" + e.getMessage());
        }
    }
}