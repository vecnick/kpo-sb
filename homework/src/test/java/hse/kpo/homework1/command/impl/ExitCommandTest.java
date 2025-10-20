package hse.kpo.homework1.command.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {

    private ExitCommand command;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        command = new ExitCommand();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testGetKey() {
        assertEquals("0", command.getKey());
    }

    @Test
    void testGetDescription() {
        assertEquals("Выход", command.getDescription());
    }

    @Test
    void testGetOrder() {
        assertEquals(1000, command.getOrder());
    }

    @Test
    void testExecute_ReturnsFalse() {
        Scanner scanner = new Scanner("");
        boolean result = command.execute(scanner);
        assertFalse(result, "Exit command should return false to stop the application");
    }

    @Test
    void testExecute_PrintsGoodbyeMessage() {
        Scanner scanner = new Scanner("");
        command.execute(scanner);
        String output = outputStream.toString();
        assertTrue(output.contains("Работа завершена"));
    }
}

