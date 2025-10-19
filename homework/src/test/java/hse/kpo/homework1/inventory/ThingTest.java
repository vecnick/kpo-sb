package hse.kpo.homework1.inventory;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ThingTest {
    @Test
    void thingCreationAndGetters() {
        Thing thing = new Thing("Lamp", 100);
        assertThat(thing.getName()).isEqualTo("Lamp");
        assertThat(thing.getNumber()).isEqualTo(100);
    }

    @Test
    void thingSetters() {
        Thing thing = new Thing("Chair", 50);
        thing.setName("Stool");
        thing.setNumber(51);
        assertThat(thing.getName()).isEqualTo("Stool");
        assertThat(thing.getNumber()).isEqualTo(51);
    }

    @Test
    void tableCreation() {
        Table table = new Table("Dining Table", 200);
        assertThat(table.getName()).isEqualTo("Dining Table");
        assertThat(table.getNumber()).isEqualTo(200);
    }

    @Test
    void tableInheritsFromThing() {
        Table table = new Table("Office Table", 201);
        table.setName("Conference Table");
        table.setNumber(202);
        assertThat(table.getName()).isEqualTo("Conference Table");
        assertThat(table.getNumber()).isEqualTo(202);
    }

    @Test
    void computerCreation() {
        Computer computer = new Computer("Dell", 300);
        assertThat(computer.getName()).isEqualTo("Dell");
        assertThat(computer.getNumber()).isEqualTo(300);
    }

    @Test
    void computerInheritsFromThing() {
        Computer computer = new Computer("HP", 301);
        computer.setName("Lenovo");
        computer.setNumber(302);
        assertThat(computer.getName()).isEqualTo("Lenovo");
        assertThat(computer.getNumber()).isEqualTo(302);
    }

    @Test
    void thingImplementsIInventory() {
        Thing thing = new Thing("Item", 999);
        thing.setNumber(1000);
        assertThat(thing.getNumber()).isEqualTo(1000);
    }
}

