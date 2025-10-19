package hse.kpo.homework1.service;

import hse.kpo.homework1.living.Monkey;
import hse.kpo.homework1.living.Tiger;
import hse.kpo.homework1.living.Rabbit;
import hse.kpo.homework1.living.Wolf;
import hse.kpo.homework1.inventory.Table;
import hse.kpo.homework1.inventory.Computer;
import hse.kpo.homework1.interfaces.IInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class ZooServiceTest {

    VetClinicService vetClinic;
    ZooService zooService;
    @BeforeEach
    void setUp() {
        vetClinic = Mockito.mock(VetClinicService.class);
        Mockito.when(vetClinic.checkHealth(Mockito.any())).thenReturn(true);
        zooService = new ZooService(vetClinic);
    }

    @Test
    void addAnimalAndCalculateFood() {
        zooService.addAnimal(new Tiger("Shere Khan", 8, 1));
        zooService.addAnimal(new Monkey("George", 3, 2, 7));
        assertThat(zooService.getTotalFood()).isEqualTo(11);
    }

    @Test
    void addThingInventoryAndInventoryReport() {
        zooService.addThing(new Table("Big", 42));
        zooService.addThing(new Computer("Acer", 43));
        assertThat(zooService.getInventoryReport()).hasSize(2);
    }

    @Test
    void filterContactAnimals() {
        zooService.addAnimal(new Tiger("Shere Khan", 8, 1));
        zooService.addAnimal(new Monkey("Goodie", 3, 2, 8));
        zooService.addAnimal(new Rabbit("Bunny", 2, 3, 4));
        List<?> contact = zooService.getContactAnimals();
        assertThat(contact).hasSize(1);
        assertThat(((Monkey)contact.get(0)).getName()).isEqualTo("Goodie");
    }

    @Test
    void vetClinicRejectsAnimal() {
        Mockito.when(vetClinic.checkHealth(Mockito.any())).thenReturn(false);
        boolean added = zooService.addAnimal(new Tiger("Sick", 10, 44));
        assertThat(added).isFalse();
        assertThat(zooService.getTotalFood()).isEqualTo(0);
    }

    @Test
    void getTotalFoodWhenNoAnimals() {
        assertThat(zooService.getTotalFood()).isEqualTo(0);
    }

    @Test
    void getContactAnimalsWhenEmpty() {
        List<?> contact = zooService.getContactAnimals();
        assertThat(contact).isEmpty();
    }

    @Test
    void getContactAnimalsWithOnlyPredators() {
        zooService.addAnimal(new Tiger("Tiger1", 8, 1));
        zooService.addAnimal(new Wolf("Wolf1", 7, 2));
        List<?> contact = zooService.getContactAnimals();
        assertThat(contact).isEmpty();
    }

    @Test
    void getContactAnimalsWithLowKindness() {
        zooService.addAnimal(new Rabbit("Shy Rabbit", 2, 3, 3));
        zooService.addAnimal(new Monkey("Grumpy Monkey", 3, 4, 5));
        List<?> contact = zooService.getContactAnimals();
        assertThat(contact).isEmpty();
    }

    @Test
    void getContactAnimalsWithExactlyKindness5() {
        zooService.addAnimal(new Rabbit("Neutral Rabbit", 2, 5, 5));
        List<?> contact = zooService.getContactAnimals();
        assertThat(contact).isEmpty();
    }

    @Test
    void getContactAnimalsWithKindnessAbove5() {
        zooService.addAnimal(new Rabbit("Friendly Rabbit", 2, 6, 6));
        zooService.addAnimal(new Monkey("Kind Monkey", 3, 7, 8));
        List<?> contact = zooService.getContactAnimals();
        assertThat(contact).hasSize(2);
    }

    @Test
    void inventoryReportContainsBothThingsAndAnimals() {
        zooService.addThing(new Table("Table1", 10));
        zooService.addAnimal(new Tiger("Tiger1", 8, 20));
        zooService.addThing(new Computer("Computer1", 30));
        zooService.addAnimal(new Rabbit("Rabbit1", 2, 40, 7));

        List<IInventory> report = zooService.getInventoryReport();
        assertThat(report).hasSize(4);
    }

    @Test
    void multipleAnimalsWithVariousFood() {
        zooService.addAnimal(new Tiger("Tiger1", 10, 1));
        zooService.addAnimal(new Wolf("Wolf1", 8, 2));
        zooService.addAnimal(new Monkey("Monkey1", 3, 3, 6));
        zooService.addAnimal(new Rabbit("Rabbit1", 2, 4, 7));

        assertThat(zooService.getTotalFood()).isEqualTo(23);
    }

    @Test
    void addAnimalReturnsTrueWhenHealthy() {
        boolean result = zooService.addAnimal(new Tiger("Healthy", 10, 100));
        assertThat(result).isTrue();
    }
}
