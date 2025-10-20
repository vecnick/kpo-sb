package hse.kpo.homework1.controller;

import hse.kpo.homework1.living.Animal;
import hse.kpo.homework1.living.Monkey;
import hse.kpo.homework1.living.Rabbit;
import hse.kpo.homework1.living.Tiger;
import hse.kpo.homework1.living.Wolf;
import hse.kpo.homework1.interfaces.IInventory;
import hse.kpo.homework1.service.ZooService;
import hse.kpo.homework1.factory.AnimalRegistry;
import hse.kpo.homework1.factory.AnimalTypeDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты для ZooConsoleController
 */
class ZooConsoleControllerTest {

    @Mock
    private ZooService zooService;

    private AnimalRegistry animalRegistry;
    private ZooConsoleController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        animalRegistry = new AnimalRegistry();
        controller = new ZooConsoleController(zooService, animalRegistry);
    }

    @Test
    void testCreateAnimalWithParams_MonkeyByName() {
        Map<String, Object> params = new HashMap<>();
        params.put("kindness", 8);

        Animal animal = controller.createAnimalWithParams("monkey", "Чита", 5, 100, params);

        assertNotNull(animal);
        assertInstanceOf(Monkey.class, animal);
        assertEquals("Чита", animal.getName());
        assertEquals(5, animal.getFood());
        assertEquals(100, animal.getNumber());
        assertEquals(8, ((Monkey) animal).getKindness());
    }

    @Test
    void testCreateAnimalWithParams_MonkeyByNumber() {
        Map<String, Object> params = new HashMap<>();
        params.put("kindness", 7);

        Animal animal = controller.createAnimalWithParams("1", "Чита", 5, 100, params);

        assertNotNull(animal);
        assertInstanceOf(Monkey.class, animal);
        assertEquals("Чита", animal.getName());
        assertEquals(7, ((Monkey) animal).getKindness());
    }

    @Test
    void testCreateAnimalWithParams_RabbitByName() {
        Map<String, Object> params = new HashMap<>();
        params.put("kindness", 9);

        Animal animal = controller.createAnimalWithParams("rabbit", "Пушистик", 2, 101, params);

        assertNotNull(animal);
        assertInstanceOf(Rabbit.class, animal);
        assertEquals("Пушистик", animal.getName());
        assertEquals(2, animal.getFood());
        assertEquals(101, animal.getNumber());
        assertEquals(9, ((Rabbit) animal).getKindness());
    }

    @Test
    void testCreateAnimalWithParams_RabbitByNumber() {
        Map<String, Object> params = new HashMap<>();
        params.put("kindness", 6);

        Animal animal = controller.createAnimalWithParams("2", "Пушистик", 2, 101, params);

        assertNotNull(animal);
        assertInstanceOf(Rabbit.class, animal);
        assertEquals(6, ((Rabbit) animal).getKindness());
    }

    @Test
    void testCreateAnimalWithParams_TigerByName() {
        Animal animal = controller.createAnimalWithParams("tiger", "Шерхан", 15, 102, new HashMap<>());

        assertNotNull(animal);
        assertInstanceOf(Tiger.class, animal);
        assertEquals("Шерхан", animal.getName());
        assertEquals(15, animal.getFood());
        assertEquals(102, animal.getNumber());
    }

    @Test
    void testCreateAnimalWithParams_TigerByNumber() {
        Animal animal = controller.createAnimalWithParams("3", "Шерхан", 15, 102, new HashMap<>());

        assertNotNull(animal);
        assertInstanceOf(Tiger.class, animal);
        assertEquals("Шерхан", animal.getName());
    }

    @Test
    void testCreateAnimalWithParams_WolfByName() {
        Animal animal = controller.createAnimalWithParams("wolf", "Акела", 10, 103, new HashMap<>());

        assertNotNull(animal);
        assertInstanceOf(Wolf.class, animal);
        assertEquals("Акела", animal.getName());
        assertEquals(10, animal.getFood());
        assertEquals(103, animal.getNumber());
    }

    @Test
    void testCreateAnimalWithParams_WolfByNumber() {
        Animal animal = controller.createAnimalWithParams("4", "Акела", 10, 103, new HashMap<>());

        assertNotNull(animal);
        assertInstanceOf(Wolf.class, animal);
        assertEquals("Акела", animal.getName());
    }

    @Test
    void testCreateAnimalWithParams_CaseInsensitive() {
        Map<String, Object> params = new HashMap<>();
        params.put("kindness", 5);

        Animal animal1 = controller.createAnimalWithParams("MONKEY", "Test1", 5, 200, params);
        Animal animal2 = controller.createAnimalWithParams("TiGeR", "Test2", 10, 201, new HashMap<>());

        assertNotNull(animal1);
        assertNotNull(animal2);
        assertInstanceOf(Monkey.class, animal1);
        assertInstanceOf(Tiger.class, animal2);
    }

    @Test
    void testCreateAnimalWithParams_UnknownType() {
        Animal animal = controller.createAnimalWithParams("elephant", "Дамбо", 50, 104, new HashMap<>());

        assertNull(animal);
    }

    @Test
    void testCreateAnimalWithParams_InvalidNumber() {
        Animal animal = controller.createAnimalWithParams("99", "Тест", 5, 105, new HashMap<>());

        assertNull(animal);
    }

    @Test
    void testCreateAnimalWithParams_EmptyParams() {
        // Для травоядных должно использоваться значение по умолчанию
        Animal animal = controller.createAnimalWithParams("monkey", "Тест", 5, 200, new HashMap<>());

        assertNull(animal); // Будет null т.к. kindness обязателен
    }

    @Test
    void testGetTypeDefinition_ValidKey() {
        var typeDefOpt = controller.getTypeDefinition("monkey");

        assertTrue(typeDefOpt.isPresent());
        assertEquals("monkey", typeDefOpt.get().getKey());
        assertEquals("Обезьяна", typeDefOpt.get().getDisplayName());
    }

    @Test
    void testGetTypeDefinition_ValidNumber() {
        var typeDefOpt = controller.getTypeDefinition("1");

        assertTrue(typeDefOpt.isPresent());
        assertEquals("monkey", typeDefOpt.get().getKey());
    }

    @Test
    void testGetTypeDefinition_InvalidKey() {
        var typeDefOpt = controller.getTypeDefinition("elephant");

        assertFalse(typeDefOpt.isPresent());
    }

    @Test
    void testAddAnimal_Success() {
        Animal animal = new Monkey("Чита", 5, 100, 8);
        when(zooService.addAnimal(any(Animal.class))).thenReturn(true);
        
        String result = controller.addAnimal(animal);
        
        assertEquals("success", result);
        verify(zooService, times(1)).addAnimal(animal);
    }

    @Test
    void testAddAnimal_HealthCheckFailed() {
        Animal animal = new Tiger("Больной тигр", 15, 106);
        when(zooService.addAnimal(any(Animal.class))).thenReturn(false);
        
        String result = controller.addAnimal(animal);
        
        assertEquals("health_check_failed", result);
        verify(zooService, times(1)).addAnimal(animal);
    }

    @Test
    void testAddAnimal_NullAnimal() {
        String result = controller.addAnimal(null);
        
        assertEquals("unknown_type", result);
        verify(zooService, never()).addAnimal(any());
    }

    @Test
    void testGetTotalFoodConsumption() {
        when(zooService.getTotalFood()).thenReturn(42);
        
        int result = controller.getTotalFoodConsumption();
        
        assertEquals(42, result);
        verify(zooService, times(1)).getTotalFood();
    }

    @Test
    void testGetTotalFoodConsumption_Zero() {
        when(zooService.getTotalFood()).thenReturn(0);
        
        int result = controller.getTotalFoodConsumption();
        
        assertEquals(0, result);
    }

    @Test
    void testGetContactZooAnimals() {
        List<Animal> mockAnimals = Arrays.asList(
            new Monkey("Чита", 5, 100, 8),
            new Rabbit("Пушистик", 2, 101, 9)
        );
        when(zooService.getContactAnimals()).thenReturn(mockAnimals);
        
        List<Animal> result = controller.getContactZooAnimals();
        
        assertEquals(2, result.size());
        assertEquals("Чита", result.get(0).getName());
        assertEquals("Пушистик", result.get(1).getName());
        verify(zooService, times(1)).getContactAnimals();
    }

    @Test
    void testGetContactZooAnimals_Empty() {
        when(zooService.getContactAnimals()).thenReturn(List.of());
        
        List<Animal> result = controller.getContactZooAnimals();
        
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetInventoryReport() {
        List<IInventory> mockInventory = Arrays.asList(
            new Monkey("Чита", 5, 100, 8),
            new Tiger("Шерхан", 15, 102)
        );
        when(zooService.getInventoryReport()).thenReturn(mockInventory);
        
        List<IInventory> result = controller.getInventoryReport();
        
        assertEquals(2, result.size());
        verify(zooService, times(1)).getInventoryReport();
    }

    @Test
    void testGetAvailableTypes() {
        List<AnimalTypeDefinition> types = controller.getAvailableTypes();

        assertNotNull(types);
        assertEquals(4, types.size());
        assertEquals("monkey", types.get(0).getKey());
        assertEquals("rabbit", types.get(1).getKey());
        assertEquals("tiger", types.get(2).getKey());
        assertEquals("wolf", types.get(3).getKey());
    }

    @Test
    void testFormatAvailableTypes() {
        String formatted = controller.formatAvailableTypes();

        assertNotNull(formatted);
        assertTrue(formatted.contains("1. Обезьяна (monkey)"));
        assertTrue(formatted.contains("2. Кролик (rabbit)"));
        assertTrue(formatted.contains("3. Тигр (tiger)"));
        assertTrue(formatted.contains("4. Волк (wolf)"));
    }
}
