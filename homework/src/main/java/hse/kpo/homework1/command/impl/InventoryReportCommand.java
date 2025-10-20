package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.command.ConsoleCommand;
import hse.kpo.homework1.controller.ZooConsoleController;
import hse.kpo.homework1.living.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Команда вывода инвентарных номеров.
 */
@Component
public class InventoryReportCommand implements ConsoleCommand {
    private final ZooConsoleController controller;

    @Autowired
    public InventoryReportCommand(ZooConsoleController controller) {
        this.controller = controller;
    }

    @Override
    public String getKey() {
        return "4";
    }

    @Override
    public String getDescription() {
        return "Вывести инвентарные номера";
    }

    @Override
    public int getOrder() {
        return 40;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.println("\n📋 Инвентарные номера:");
        controller.getInventoryReport().forEach(item -> {
            String itemName = item instanceof Animal an ? an.getName() :
                            ((hse.kpo.homework1.inventory.Thing)item).getName();
            System.out.println("  " + item.getClass().getSimpleName() + ": " + itemName + ", №" + item.getNumber());
        });
        return true;
    }
}

