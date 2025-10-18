package hse.kpo.homework1.service;

import hse.kpo.homework1.living.Monkey;
import hse.kpo.homework1.living.Tiger;
import hse.kpo.homework1.living.Rabbit;
import hse.kpo.homework1.living.Wolf;
import hse.kpo.homework1.inventory.Table;
import hse.kpo.homework1.inventory.Computer;
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
}
