package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.command.ConsoleCommand;
import hse.kpo.homework1.controller.ZooConsoleController;
import hse.kpo.homework1.factory.AnimalParameter;
import hse.kpo.homework1.factory.AnimalTypeDefinition;
import hse.kpo.homework1.living.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Команда добавления животного в зоопарк.
 * Следует принципу Single Responsibility - отвечает только за добавление животного.
 */
@Component
public class AddAnimalCommand implements ConsoleCommand {
    private final ZooConsoleController controller;

    @Autowired
    public AddAnimalCommand(ZooConsoleController controller) {
        this.controller = controller;
    }

    @Override
    public String getKey() {
        return "1";
    }

    @Override
    public String getDescription() {
        return "Добавить животное";
    }

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.println("\n" + controller.formatAvailableTypes());
        System.out.print("Введите номер или название типа животного: ");
        String type = scanner.nextLine();

        Optional<AnimalTypeDefinition> typeDefOpt = controller.getTypeDefinition(type);
        if (typeDefOpt.isEmpty()) {
            System.out.println("✗ Неизвестный тип животного.");
            return true;
        }

        AnimalTypeDefinition typeDef = typeDefOpt.get();

        System.out.print("Имя: ");
        String name = scanner.nextLine();

        System.out.print("Потребление корма, кг/день: ");
        int food = Integer.parseInt(scanner.nextLine());

        System.out.print("Инвентарный номер: ");
        int num = Integer.parseInt(scanner.nextLine());

        // Запрашиваем дополнительные параметры
        Map<String, Object> additionalParams = new HashMap<>();
        for (AnimalParameter param : typeDef.getAdditionalParameters()) {
            System.out.print(param.getDisplayName() + ": ");
            String value = scanner.nextLine();
            try {
                Object parsedValue = param.parseValue(value);
                additionalParams.put(param.getName(), parsedValue);
            } catch (Exception e) {
                System.out.println("⚠ Ошибка ввода параметра, используется значение по умолчанию");
                additionalParams.put(param.getName(), param.getDefaultValue());
            }
        }

        Animal animal = controller.createAnimalWithParams(type, name, food, num, additionalParams);
        String result = controller.addAnimal(animal);

        switch (result) {
            case "success":
                System.out.println("✓ Животное успешно добавлено!");
                break;
            case "health_check_failed":
                System.out.println("✗ Животное не прошло проверку здоровья.");
                break;
            case "unknown_type":
                System.out.println("✗ Неизвестный тип животного.");
                break;
        }

        return true;
    }
}