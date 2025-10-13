package hse.kpo;

import hse.kpo.domain.alive.Monkey;
import hse.kpo.domain.alive.Tiger;
import hse.kpo.domain.inventory.Computer;
import hse.kpo.services.VeterinaryClinic;
import hse.kpo.services.ZooService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ZooServiceTest {

    @Mock
    private VeterinaryClinic veterinaryClinic;

    private ZooService zooService;

    @BeforeEach
    void setUp() {
        zooService = new ZooService(veterinaryClinic);
    }

    @Test
    void testAddHealthyAnimal() {
        Monkey monkey = new Monkey("Monkey", 2, 1001, 7, "color");
        when(veterinaryClinic.checkHealth(any())).thenReturn(true);

        boolean result = zooService.addAnimal(monkey);

        assertTrue(result);
        assertEquals(1, zooService.getAnimalCount());
        assertTrue(monkey.isHealthy());
    }

    @Test
    void testAddUnhealthyAnimal() {
        Tiger tiger = new Tiger("Tiger", 10, 1002, 100);
        when(veterinaryClinic.checkHealth(any())).thenReturn(false);

        boolean result = zooService.addAnimal(tiger);

        assertFalse(result);
        assertEquals(0, zooService.getAnimalCount());
        assertFalse(tiger.isHealthy());
    }

    @Test
    void testFoodConsumptionCalculation() {
        Monkey monkey = new Monkey("Monkey", 2, 1001, 7, "color");
        Tiger tiger = new Tiger("Tiger", 10, 1002, 100);
        when(veterinaryClinic.checkHealth(any())).thenReturn(true);

        zooService.addAnimal(monkey);
        zooService.addAnimal(tiger);

        assertEquals(12, zooService.getTotalFoodConsumption());
    }

    @Test
    void testContactZooAnimals() {
        Monkey contactMonkey = new Monkey("Good", 2, 1001, 8, "color");
        Monkey nonContactMonkey = new Monkey("Bad", 2, 1002, 3, "color");
        when(veterinaryClinic.checkHealth(any())).thenReturn(true);

        zooService.addAnimal(contactMonkey);
        zooService.addAnimal(nonContactMonkey);

        var contactAnimals = zooService.getAnimalsForContactZoo();
        assertEquals(1, contactAnimals.size());
        assertEquals("Good", contactAnimals.getFirst().getName());
    }

    @Test
    void testInventoryManagement() {
        Computer computer = new Computer("Computer", 2001, "processor");

        zooService.addInventoryItem(computer);

        assertEquals(1, zooService.getAllInventory().size());
        assertEquals("Computer", zooService.getAllInventory().getFirst().getName());
    }

    @Test
    void testCheckHealth() {
        VeterinaryClinic clinic = new VeterinaryClinic();
        Monkey monkey = new Monkey("five_", 2, 1001, 7, "Brown");

        boolean result = clinic.checkHealth(monkey);

        assertFalse(result);
    }
}