package hse.kpo;

import hse.kpo.config.AppConfig;
import hse.kpo.domain.alive.*;
import hse.kpo.domain.inventory.*;
import hse.kpo.services.ZooService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ZooService zooService = context.getBean(ZooService.class);
        Scanner scanner = new Scanner(System.in);

        zooService.addInventoryItem(new Table("Table", 1001, "Wood"));
        zooService.addInventoryItem(new Computer("Computer", 1002, "Rock"));

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addAnimalMenu(scanner, zooService);
                    break;
                case "2":
                    showAnimalCount(zooService);
                    break;
                case "3":
                    showFoodConsumption(zooService);
                    break;
                case "4":
                    showContactZooAnimals(zooService);
                    break;
                case "5":
                    showInventory(zooService);
                    break;
                case "0":
                    running = false;
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("Try again.");
            }
        }

        scanner.close();
        context.close();
    }

    private static void printMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add new animal");
        System.out.println("2. View number of animals");
        System.out.println("3. Food consumption");
        System.out.println("4. Contact Zoo Animals");
        System.out.println("5. View Inventory");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void addAnimalMenu(Scanner scanner, ZooService zooService) {
        System.out.println("\n--- Adding new animal ---");
        System.out.println("Choose animal type:");
        System.out.println("1. Monkey");
        System.out.println("2. Rabbit");
        System.out.println("3. Tiger");
        System.out.println("4. Wolf");
        System.out.print("Choose option: ");

        String typeChoice = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter amount of food consumption (kg / day): ");
        int food = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter ID: ");
        int number = Integer.parseInt(scanner.nextLine());

        Animal animal = null;

        switch (typeChoice) {
            case "1":
                System.out.print("Enter kindness level (1-10): ");
                int kindness = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter color: ");
                String furColor = scanner.nextLine();
                animal = new Monkey(name, food, number, kindness, furColor);
                break;
            case "2":
                System.out.print("Enter kindness level (1-10): ");
                kindness = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter ear length: ");
                int earLength = Integer.parseInt(scanner.nextLine());
                animal = new Rabbit(name, food, number, kindness, earLength);
                break;
            case "3":
                System.out.print("Enter number of strips: ");
                int stripes = Integer.parseInt(scanner.nextLine());
                animal = new Tiger(name, food, number, stripes);
                break;
            case "4":
                System.out.print("Enter role: ");
                String packRole = scanner.nextLine();
                animal = new Wolf(name, food, number, packRole);
                break;
            default:
                System.out.println("Try again");
                return;
        }

        zooService.addAnimal(animal);
    }

    private static void showAnimalCount(ZooService zooService) {
        System.out.println("\n--- Animals list ---");
        System.out.println("Animals total: " + zooService.getAnimalCount());

        zooService.getAllAnimals().forEach(animal ->
                System.out.println("- " + animal.getName() + " (ID = " + animal.getNumber() + ")"));
    }

    private static void showFoodConsumption(ZooService zooService) {
        System.out.println("\n--- Food Consumption ---");
        System.out.println("Total food: " + zooService.getTotalFoodConsumption() + " kg");

        zooService.getAllAnimals().forEach(animal ->
                System.out.println("- " + animal.getName() + ": " + animal.getFood() + " kg/day"));
    }

    private static void showContactZooAnimals(ZooService zooService) {
        System.out.println("\n--- Contact zoo animals ---");
        var contactAnimals = zooService.getAnimalsForContactZoo();

        if (contactAnimals.isEmpty()) {
            System.out.println("No animals allowed for contact zoo.");
        } else {
            contactAnimals.forEach(animal -> {
                if (animal instanceof Herbo herbo) {
                    System.out.println("- " + animal.getName() + " (Kindness Level: " + herbo.getKindnessLevel() + ")");
                }
            });
        }
    }

    private static void showInventory(ZooService zooService) {
        System.out.println("\n--- Zoo inventory ---");
        zooService.getAllInventory().forEach(item ->
                System.out.println("- " + item.getName() + " (ID = " + item.getNumber() + ")"));
    }
}