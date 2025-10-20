package hse.kpo.homework1.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CommandRegistryTest {

    private CommandRegistry registry;
    private TestCommand command1;
    private TestCommand command2;
    private TestCommand command3;

    @BeforeEach
    void setUp() {
        command1 = new TestCommand("1", "First command", 10);
        command2 = new TestCommand("2", "Second command", 20);
        command3 = new TestCommand("3", "Third command", 5);
        
        List<ConsoleCommand> commands = Arrays.asList(command1, command2, command3);
        registry = new CommandRegistry(commands);
    }

    @Test
    void testGetCommand_ExistingCommand() {
        Optional<ConsoleCommand> result = registry.getCommand("1");
        assertTrue(result.isPresent());
        assertEquals(command1, result.get());
    }

    @Test
    void testGetCommand_NonExistingCommand() {
        Optional<ConsoleCommand> result = registry.getCommand("999");
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllCommands_ReturnsSortedByOrder() {
        List<ConsoleCommand> allCommands = registry.getAllCommands();
        assertEquals(3, allCommands.size());
        // Проверяем порядок: command3 (order=5), command1 (order=10), command2 (order=20)
        assertEquals(command3, allCommands.get(0));
        assertEquals(command1, allCommands.get(1));
        assertEquals(command2, allCommands.get(2));
    }

    @Test
    void testGetAllCommands_ReturnsUnmodifiableList() {
        List<ConsoleCommand> allCommands = registry.getAllCommands();
        assertThrows(UnsupportedOperationException.class, () -> allCommands.add(new TestCommand("4", "Fourth", 30)));
    }

    @Test
    void testFormatMenu() {
        String menu = registry.formatMenu();
        assertNotNull(menu);
        assertTrue(menu.contains("=== МЕНЮ ==="));
        assertTrue(menu.contains("3. Third command"));
        assertTrue(menu.contains("1. First command"));
        assertTrue(menu.contains("2. Second command"));
    }

    @Test
    void testFormatMenu_ContainsAllCommands() {
        String menu = registry.formatMenu();
        assertTrue(menu.contains(command1.getKey()));
        assertTrue(menu.contains(command1.getDescription()));
        assertTrue(menu.contains(command2.getKey()));
        assertTrue(menu.contains(command2.getDescription()));
        assertTrue(menu.contains(command3.getKey()));
        assertTrue(menu.contains(command3.getDescription()));
    }

    // Вспомогательный класс для тестирования
    private static class TestCommand implements ConsoleCommand {
        private final String key;
        private final String description;
        private final int order;

        TestCommand(String key, String description, int order) {
            this.key = key;
            this.description = description;
            this.order = order;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public int getOrder() {
            return order;
        }

        @Override
        public boolean execute(Scanner scanner) {
            return true;
        }
    }
}

