package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.controller.ZooConsoleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodReportCommandTest {

    @Mock
    private ZooConsoleController controller;

    private FoodReportCommand command;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new FoodReportCommand(controller);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testGetKey() {
        assertEquals("2", command.getKey());
    }

    @Test
    void testGetDescription() {
        assertEquals("Вывести отчёт по корму", command.getDescription());
    }

    @Test
    void testGetOrder() {
        assertEquals(20, command.getOrder());
    }

    @Test
    void testExecute_ReturnsTrue() {
        when(controller.getTotalFoodConsumption()).thenReturn(100);
        Scanner scanner = new Scanner("");

        boolean result = command.execute(scanner);

        assertTrue(result, "Command should return true to continue application");
    }

    @Test
    void testExecute_CallsController() {
        when(controller.getTotalFoodConsumption()).thenReturn(150);
        Scanner scanner = new Scanner("");

        command.execute(scanner);

        verify(controller, times(1)).getTotalFoodConsumption();
    }

    @Test
    void testExecute_PrintsFoodConsumption() {
        when(controller.getTotalFoodConsumption()).thenReturn(250);
        Scanner scanner = new Scanner("");

        command.execute(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("250"));
        assertTrue(output.contains("кг/день"));
    }
}
