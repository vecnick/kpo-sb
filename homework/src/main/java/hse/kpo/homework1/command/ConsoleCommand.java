package hse.kpo.homework1.command;

import java.util.Scanner;

/**
 * Интерфейс команды консольного приложения.
 * Следует принципу Command Pattern - инкапсулирует команду как объект.
 * Позволяет добавлять новые команды без изменения основного кода приложения.
 */
public interface ConsoleCommand {
    /**
     * Возвращает ключ команды для выбора пользователем
     */
    String getKey();

    /**
     * Возвращает описание команды для отображения в меню
     */
    String getDescription();

    /**
     * Выполняет команду
     * @param scanner сканер для ввода данных от пользователя
     * @return true если нужно продолжить работу приложения, false для выхода
     */
    boolean execute(Scanner scanner);

    /**
     * Определяет порядок отображения команды в меню (чем меньше, тем выше)
     */
    default int getOrder() {
        return 100;
    }
}