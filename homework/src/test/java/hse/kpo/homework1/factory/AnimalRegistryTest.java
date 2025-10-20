package hse.kpo.homework1.factory;

import hse.kpo.homework1.living.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-тесты для AnimalRegistry
 */
class AnimalRegistryTest {

    private AnimalRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new AnimalRegistry();
    }

    @Test
    void testGetTypeInfo_ValidKey() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfo("monkey");

        assertTrue(info.isPresent());
        assertEquals("monkey", info.get().getKey());
        assertEquals("Обезьяна", info.get().getDisplayName());
        assertFalse(info.get().getAdditionalParameters().isEmpty());
    }

    @Test
    void testGetTypeInfo_CaseInsensitive() {
        Optional<AnimalTypeDefinition> info1 = registry.getTypeInfo("TIGER");
        Optional<AnimalTypeDefinition> info2 = registry.getTypeInfo("TiGeR");

        assertTrue(info1.isPresent());
        assertTrue(info2.isPresent());
        assertEquals("tiger", info1.get().getKey());
        assertEquals("tiger", info2.get().getKey());
    }

    @Test
    void testGetTypeInfo_InvalidKey() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfo("elephant");

        assertFalse(info.isPresent());
    }

    @Test
    void testGetTypeInfoByNumber_Valid() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfoByNumber(1);

        assertTrue(info.isPresent());
        assertEquals("monkey", info.get().getKey());
    }

    @Test
    void testGetTypeInfoByNumber_AllNumbers() {
        Optional<AnimalTypeDefinition> info1 = registry.getTypeInfoByNumber(1);
        Optional<AnimalTypeDefinition> info2 = registry.getTypeInfoByNumber(2);
        Optional<AnimalTypeDefinition> info3 = registry.getTypeInfoByNumber(3);
        Optional<AnimalTypeDefinition> info4 = registry.getTypeInfoByNumber(4);

        assertTrue(info1.isPresent());
        assertTrue(info2.isPresent());
        assertTrue(info3.isPresent());
        assertTrue(info4.isPresent());
        
        assertEquals("monkey", info1.get().getKey());
        assertEquals("rabbit", info2.get().getKey());
        assertEquals("tiger", info3.get().getKey());
        assertEquals("wolf", info4.get().getKey());
    }

    @Test
    void testGetTypeInfoByNumber_Zero() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfoByNumber(0);
        assertFalse(info.isPresent());
    }

    @Test
    void testGetTypeInfoByNumber_Negative() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfoByNumber(-1);
        assertFalse(info.isPresent());
    }

    @Test
    void testGetTypeInfoByNumber_OutOfBounds() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfoByNumber(999);
        assertFalse(info.isPresent());
    }

    @Test
    void testGetAllTypes() {
        List<AnimalTypeDefinition> types = registry.getAllTypes();

        assertNotNull(types);
        assertEquals(4, types.size());
    }

    @Test
    void testGetAllTypes_Order() {
        List<AnimalTypeDefinition> types = registry.getAllTypes();

        assertEquals("monkey", types.get(0).getKey());
        assertEquals("rabbit", types.get(1).getKey());
        assertEquals("tiger", types.get(2).getKey());
        assertEquals("wolf", types.get(3).getKey());
    }

    @Test
    void testGetAllTypes_Immutable() {
        List<AnimalTypeDefinition> types = registry.getAllTypes();

        assertThrows(UnsupportedOperationException.class, () -> types.add(null));
    }

    @Test
    void testGetTypeCount() {
        assertEquals(4, registry.getTypeCount());
    }

    @Test
    void testFactoryCreatesCorrectType_Monkey() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfo("monkey");
        assertTrue(info.isPresent());
        
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("kindness", 8);
        Animal animal = info.get().createAnimal("Чита", 5, 100, params);

        assertNotNull(animal);
        assertInstanceOf(Monkey.class, animal);
        assertEquals("Чита", animal.getName());
        assertEquals(5, animal.getFood());
        assertEquals(100, animal.getNumber());
        assertEquals(8, ((Monkey) animal).getKindness());
    }

    @Test
    void testFactoryCreatesCorrectType_Rabbit() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfo("rabbit");
        assertTrue(info.isPresent());
        
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("kindness", 7);
        Animal animal = info.get().createAnimal("Пушистик", 2, 101, params);

        assertNotNull(animal);
        assertInstanceOf(Rabbit.class, animal);
    }

    @Test
    void testFactoryCreatesCorrectType_Tiger() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfo("tiger");
        assertTrue(info.isPresent());
        
        Animal animal = info.get().createAnimal("Шерхан", 15, 102, new java.util.HashMap<>());

        assertNotNull(animal);
        assertInstanceOf(Tiger.class, animal);
    }

    @Test
    void testFactoryCreatesCorrectType_Wolf() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfo("wolf");
        assertTrue(info.isPresent());
        
        Animal animal = info.get().createAnimal("Акела", 10, 103, new java.util.HashMap<>());

        assertNotNull(animal);
        assertInstanceOf(Wolf.class, animal);
    }

    @Test
    void testAdditionalParameters_Herbivores() {
        Optional<AnimalTypeDefinition> monkeyInfo = registry.getTypeInfo("monkey");
        Optional<AnimalTypeDefinition> rabbitInfo = registry.getTypeInfo("rabbit");

        assertTrue(monkeyInfo.isPresent());
        assertTrue(rabbitInfo.isPresent());

        assertFalse(monkeyInfo.get().getAdditionalParameters().isEmpty());
        assertFalse(rabbitInfo.get().getAdditionalParameters().isEmpty());

        assertEquals("kindness", monkeyInfo.get().getAdditionalParameters().get(0).getName());
        assertEquals("kindness", rabbitInfo.get().getAdditionalParameters().get(0).getName());
    }

    @Test
    void testAdditionalParameters_Predators() {
        Optional<AnimalTypeDefinition> tigerInfo = registry.getTypeInfo("tiger");
        Optional<AnimalTypeDefinition> wolfInfo = registry.getTypeInfo("wolf");

        assertTrue(tigerInfo.isPresent());
        assertTrue(wolfInfo.isPresent());

        assertTrue(tigerInfo.get().getAdditionalParameters().isEmpty());
        assertTrue(wolfInfo.get().getAdditionalParameters().isEmpty());
    }

    @Test
    void testGetParameter() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfo("monkey");
        assertTrue(info.isPresent());

        Optional<AnimalParameter> paramOpt = info.get().getParameter("kindness");
        assertTrue(paramOpt.isPresent());

        AnimalParameter param = paramOpt.get();
        assertEquals("kindness", param.getName());
        assertEquals("Доброта (0-10)", param.getDisplayName());
        assertTrue(param.isRequired());
    }

    @Test
    void testCreateAnimal_MissingRequiredParameter() {
        Optional<AnimalTypeDefinition> info = registry.getTypeInfo("monkey");
        assertTrue(info.isPresent());

        // Пытаемся создать без обязательного параметра kindness
        assertThrows(IllegalArgumentException.class, () -> info.get().createAnimal("Тест", 5, 200, new java.util.HashMap<>()));
    }
}
