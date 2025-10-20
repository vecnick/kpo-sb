package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.command.ConsoleCommand;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Команда выхода из приложения.
 */
@Component
public class ExitCommand implements ConsoleCommand {

    @Override
    public String getKey() {
        return "0";
    }

    @Override
    public String getDescription() {
        return "Выход";
    }

    @Override
    public int getOrder() {
        return 1000; // В конце меню
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.println("\n👋 Работа завершена.");
        return false; // Остановить приложение
    }
}
