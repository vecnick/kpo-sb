package hse.kpo.homework1.command.impl;

import hse.kpo.homework1.controller.ZooConsoleController;
import hse.kpo.homework1.factory.AnimalParameter;
import hse.kpo.homework1.factory.AnimalTypeDefinition;
import hse.kpo.homework1.living.Monkey;
import hse.kpo.homework1.living.Rabbit;
import hse.kpo.homework1.living.Tiger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AddAnimalCommandTest {

    @Mock
    private ZooConsoleController controller;

    private AddAnimalCommand command;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new AddAnimalCommand(controller);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testGetKey() {
        assertEquals("1", command.getKey());
    }

    @Test
    void testGetDescription() {
        assertEquals("Добавить животное", command.getDescription());
    }

    @Test
    void testGetOrder() {
        assertEquals(10, command.getOrder());
    }

    @Test
    void testExecute_UnknownType() {
        when(controller.formatAvailableTypes()).thenReturn("Доступные типы");
        when(controller.getTypeDefinition(anyString())).thenReturn(Optional.empty());

        String input = "неизвестный_тип\n";
        Scanner scanner = new Scanner(input);

        boolean result = command.execute(scanner);

        assertTrue(result);
        String output = outputStream.toString();
        assertTrue(output.contains("Неизвестный тип животного"));
    }

    @Test
    void testExecute_SuccessfulAdd() {
        // Подготовка
        AnimalTypeDefinition typeDef = mock(AnimalTypeDefinition.class);
        when(typeDef.getAdditionalParameters()).thenReturn(Collections.emptyList());

        when(controller.formatAvailableTypes()).thenReturn("Доступные типы");
        when(controller.getTypeDefinition("rabbit")).thenReturn(Optional.of(typeDef));

        Rabbit rabbit = new Rabbit("Пушистик", 2, 101, 8);
        when(controller.createAnimalWithParams(eq("rabbit"), eq("Пушистик"), eq(2), eq(101), any()))
            .thenReturn(rabbit);
        when(controller.addAnimal(any())).thenReturn("success");

        String input = "rabbit\nПушистик\n2\n101\n";
        Scanner scanner = new Scanner(input);

        boolean result = command.execute(scanner);

        assertTrue(result);
        String output = outputStream.toString();
        assertTrue(output.contains("успешно добавлено"));
        verify(controller).addAnimal(any());
    }

    @Test
    void testExecute_HealthCheckFailed() {
        AnimalTypeDefinition typeDef = mock(AnimalTypeDefinition.class);
        when(typeDef.getAdditionalParameters()).thenReturn(Collections.emptyList());

        when(controller.formatAvailableTypes()).thenReturn("Доступные типы");
        when(controller.getTypeDefinition("tiger")).thenReturn(Optional.of(typeDef));

        Tiger tiger = new Tiger("Тигруля", 10, 102);
        when(controller.createAnimalWithParams(eq("tiger"), eq("Тигруля"), eq(10), eq(102), any()))
            .thenReturn(tiger);
        when(controller.addAnimal(any())).thenReturn("health_check_failed");

        String input = "tiger\nТигруля\n10\n102\n";
        Scanner scanner = new Scanner(input);

        boolean result = command.execute(scanner);

        assertTrue(result);
        String output = outputStream.toString();
        assertTrue(output.contains("не прошло проверку здоровья"));
    }

    @Test
    void testExecute_WithAdditionalParameters() {
        // Подготовка параметра
        AnimalParameter param = mock(AnimalParameter.class);
        when(param.getDisplayName()).thenReturn("Доброта");
        when(param.getName()).thenReturn("kindness");
        when(param.parseValue("8")).thenReturn(8);

        AnimalTypeDefinition typeDef = mock(AnimalTypeDefinition.class);
        when(typeDef.getAdditionalParameters()).thenReturn(Collections.singletonList(param));

        when(controller.formatAvailableTypes()).thenReturn("Доступные типы");
        when(controller.getTypeDefinition("rabbit")).thenReturn(Optional.of(typeDef));

        Rabbit rabbit = new Rabbit("Пушок", 2, 103, 8);
        when(controller.createAnimalWithParams(eq("rabbit"), eq("Пушок"), eq(2), eq(103), any()))
            .thenReturn(rabbit);
        when(controller.addAnimal(any())).thenReturn("success");

        String input = "rabbit\nПушок\n2\n103\n8\n";
        Scanner scanner = new Scanner(input);

        boolean result = command.execute(scanner);

        assertTrue(result);
        verify(controller).createAnimalWithParams(eq("rabbit"), eq("Пушок"), eq(2), eq(103), any());
    }

    @Test
    void testExecute_ParameterParseError() {
        AnimalParameter param = mock(AnimalParameter.class);
        when(param.getDisplayName()).thenReturn("Доброта");
        when(param.getName()).thenReturn("kindness");
        when(param.getDefaultValue()).thenReturn(5);
        when(param.parseValue(anyString())).thenThrow(new NumberFormatException());

        AnimalTypeDefinition typeDef = mock(AnimalTypeDefinition.class);
        when(typeDef.getAdditionalParameters()).thenReturn(Collections.singletonList(param));

        when(controller.formatAvailableTypes()).thenReturn("Доступные типы");
        when(controller.getTypeDefinition("monkey")).thenReturn(Optional.of(typeDef));

        Monkey monkey = new Monkey("Чита", 3, 104, 5);
        when(controller.createAnimalWithParams(eq("monkey"), eq("Чита"), eq(3), eq(104), any()))
            .thenReturn(monkey);
        when(controller.addAnimal(any())).thenReturn("success");

        String input = "monkey\nЧита\n3\n104\nневерное_значение\n";
        Scanner scanner = new Scanner(input);

        boolean result = command.execute(scanner);

        assertTrue(result);
        String output = outputStream.toString();
        assertTrue(output.contains("используется значение по умолчанию"));
    }

    @Test
    void testExecute_DisplaysAvailableTypes() {
        when(controller.formatAvailableTypes()).thenReturn("=== Доступные типы ===");
        when(controller.getTypeDefinition(anyString())).thenReturn(Optional.empty());

        String input = "unknown\n";
        Scanner scanner = new Scanner(input);

        command.execute(scanner);

        verify(controller).formatAvailableTypes();
        String output = outputStream.toString();
        assertTrue(output.contains("Доступные типы"));
    }
}
