package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.controller.ZooConsoleController;
import hse.kpo.homework1.interfaces.IInventory;
import hse.kpo.homework1.inventory.Thing;
import hse.kpo.homework1.living.Rabbit;
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

class InventoryReportCommandTest {

    @Mock
    private ZooConsoleController controller;

    private InventoryReportCommand command;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new InventoryReportCommand(controller);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testGetKey() {
        assertEquals("4", command.getKey());
    }

    @Test
    void testGetDescription() {
        assertEquals("Вывести инвентарные номера", command.getDescription());
    }

    @Test
    void testGetOrder() {
        assertEquals(40, command.getOrder());
    }

    @Test
    void testExecute_ReturnsTrue() {
        when(controller.getInventoryReport()).thenReturn(Collections.emptyList());
        Scanner scanner = new Scanner("");

        boolean result = command.execute(scanner);

        assertTrue(result, "Command should return true to continue application");
    }

    @Test
    void testExecute_WithItems() {
        Rabbit rabbit = new Rabbit("Барсик", 3, 201, 9);
        Thing thing = new Thing("Миска", 301);
        List<IInventory> items = Arrays.asList(rabbit, thing);

        when(controller.getInventoryReport()).thenReturn(items);
        Scanner scanner = new Scanner("");

        command.execute(scanner);

        String output = outputStream.toString();
        assertTrue(output.contains("Инвентарные номера"));
        assertTrue(output.contains("Барсик"));
        assertTrue(output.contains("201"));
        assertTrue(output.contains("Миска"));
        assertTrue(output.contains("301"));
        verify(controller, times(1)).getInventoryReport();
    }

    @Test
    void testExecute_CallsController() {
        when(controller.getInventoryReport()).thenReturn(Collections.emptyList());
        Scanner scanner = new Scanner("");

        command.execute(scanner);

        verify(controller, times(1)).getInventoryReport();
    }
}
