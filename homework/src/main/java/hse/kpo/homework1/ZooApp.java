package hse.kpo.homework1;

import hse.kpo.homework1.inventory.Table;
import hse.kpo.homework1.inventory.Computer;
import hse.kpo.homework1.service.ZooService;
import hse.kpo.homework1.command.CommandRegistry;
import hse.kpo.homework1.command.ConsoleCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Scanner;

/**
 * Главный класс приложения.
 * Использует паттерн Command для гибкого управления командами.
 * Для добавления новой команды достаточно создать класс, реализующий ConsoleCommand,
 * и добавить @Component - Spring автоматически зарегистрирует команду.
 */
@SpringBootApplication
public class ZooApp {
    public static void main(String[] args) {
        SpringApplication.run(ZooApp.class, args);
    }

    @Bean
    CommandLineRunner run(@Autowired ZooService zooService,
                         @Autowired CommandRegistry commandRegistry) {
        return args -> {
            Scanner sc = new Scanner(System.in);
            System.out.println("Zoo Management Console Application стартовал.\n");

            zooService.addThing(new Table("Овальный", 1001));
            zooService.addThing(new Computer("Lenovo ZooPC", 1452));

            boolean running = true;
            while (running) {
                // Выводим меню
                System.out.print(commandRegistry.formatMenu());
                System.out.print("Выберите действие: ");

                String input = sc.nextLine();

                // Находим и выполняем команду
                var commandOpt = commandRegistry.getCommand(input);
                if (commandOpt.isPresent()) {
                    ConsoleCommand command = commandOpt.get();
                    try {
                        running = command.execute(sc);
                    } catch (Exception e) {
                        System.out.println("⚠ Ошибка выполнения команды: " + e.getMessage());
                    }
                } else {
                    System.out.println("⚠ Неизвестная команда");
                }
            }
        };
    }
}
