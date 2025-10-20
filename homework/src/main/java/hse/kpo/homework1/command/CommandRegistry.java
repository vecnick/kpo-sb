package hse.kpo.homework1.command;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Регистр всех доступных команд консольного приложения.
 * Следует принципам:
 * - Single Responsibility: управляет командами
 * - Open/Closed: открыт для добавления новых команд без модификации
 * 
 * Для добавления новой команды достаточно создать класс, реализующий ConsoleCommand,
 * и добавить его в Spring контекст - он автоматически зарегистрируется.
 */
@Component
public class CommandRegistry {
    private final Map<String, ConsoleCommand> commands = new LinkedHashMap<>();
    private final List<ConsoleCommand> orderedCommands = new ArrayList<>();

    public CommandRegistry(List<ConsoleCommand> commandList) {
        for (ConsoleCommand command : commandList) {
            commands.put(command.getKey().toLowerCase(), command);
        }
        
        orderedCommands.addAll(commandList);
        orderedCommands.sort(Comparator.comparingInt(ConsoleCommand::getOrder));
    }

    /**
     * Получает команду по ключу
     */
    public Optional<ConsoleCommand> getCommand(String key) {
        return Optional.ofNullable(commands.get(key.toLowerCase()));
    }

    /**
     * Возвращает список всех команд в порядке отображения
     */
    public List<ConsoleCommand> getAllCommands() {
        return Collections.unmodifiableList(orderedCommands);
    }

    /**
     * Форматирует меню команд
     */
    public String formatMenu() {
        StringBuilder sb = new StringBuilder("\n=== МЕНЮ ===\n");
        for (ConsoleCommand command : orderedCommands) {
            sb.append(command.getKey())
              .append(". ")
              .append(command.getDescription())
              .append("\n");
        }
        return sb.toString();
    }
}

