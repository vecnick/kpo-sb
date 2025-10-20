package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.command.ConsoleCommand;
import hse.kpo.homework1.controller.ZooConsoleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Команда вывода списка животных для контактного зоопарка.
 */
@Component
public class ContactZooCommand implements ConsoleCommand {
    private final ZooConsoleController controller;

    @Autowired
    public ContactZooCommand(ZooConsoleController controller) {
        this.controller = controller;
    }

    @Override
    public String getKey() {
        return "3";
    }

    @Override
    public String getDescription() {
        return "Список для контактного зоопарка";
    }

    @Override
    public int getOrder() {
        return 30;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.println("\n🐰 Для контактного зоопарка подходят:");
        var contactAnimals = controller.getContactZooAnimals();
        if (contactAnimals.isEmpty()) {
            System.out.println("  (нет подходящих животных)");
        } else {
            contactAnimals.forEach(a -> System.out.println("  - " + a.getName()));
        }
        return true;
    }
}

