package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.controller.ZooConsoleController;
import hse.kpo.homework1.living.Animal;
import hse.kpo.homework1.living.Rabbit;
import hse.kpo.homework1.living.Tiger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactZooCommandTest {

    @Mock
    private ZooConsoleController controller;

    private ContactZooCommand command;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new ContactZooCommand(controller);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testGetKey() {
        assertEquals("3", command.getKey());
    }

    @Test
    void testGetDescription() {
        assertEquals("Список для контактного зоопарка", command.getDescription());
    }

    @Test
    void testGetOrder() {
        assertEquals(30, command.getOrder());
    }

    @Test
    void testExecute_ReturnsTrue() {
        when(controller.getContactZooAnimals()).thenReturn(Collections.emptyList());
        Scanner scanner = new Scanner("");

        boolean result = command.execute(scanner);

        assertTrue(result, "Command should return true to continue application");
    }

    @Test
    void testExecute_WithAnimals() {
        Rabbit rabbit = new Rabbit("Пушистик", 2, 101, 8);
        Tiger tiger = new Tiger("Шерхан", 5, 102);
        List<Animal> animals = Arrays.asList(rabbit, tiger);

        when(controller.getContactZooAnimals()).thenReturn(animals);
        Scanner scanner = new Scanner("");

        command.execute(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("Пушистик"));
        assertTrue(output.contains("Шерхан"));
        verify(controller, times(1)).getContactZooAnimals();
    }

    @Test
    void testExecute_WithNoAnimals() {
        when(controller.getContactZooAnimals()).thenReturn(Collections.emptyList());
        Scanner scanner = new Scanner("");

        command.execute(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("нет подходящих животных"));
    }
}
