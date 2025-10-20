package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.command.ConsoleCommand;
import hse.kpo.homework1.controller.ZooConsoleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Команда вывода отчета по корму.
 */
@Component
public class FoodReportCommand implements ConsoleCommand {
    private final ZooConsoleController controller;

    @Autowired
    public FoodReportCommand(ZooConsoleController controller) {
        this.controller = controller;
    }

    @Override
    public String getKey() {
        return "2";
    }

    @Override
    public String getDescription() {
        return "Вывести отчёт по корму";
    }

    @Override
    public int getOrder() {
        return 20;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.println("\n📊 Всего нужно корма: " + controller.getTotalFoodConsumption() + " кг/день");
        return true;
    }
}

