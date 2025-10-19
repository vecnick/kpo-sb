package hse.kpo.homework1.living;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AnimalTest {
    @Test
    void animalSettersLombok() {
        Predator p = new Predator("Volf", 15, 77);
        p.setName("Willy");
        p.setFood(7);
        p.setNumber(2);
        assertThat(p.getName()).isEqualTo("Willy");
        assertThat(p.getFood()).isEqualTo(7);
        assertThat(p.getNumber()).isEqualTo(2);
    }

    @Test
    void herboCreationAndGetters() {
        Herbo herbo = new Herbo("Bambi", 5, 10, 8);
        assertThat(herbo.getName()).isEqualTo("Bambi");
        assertThat(herbo.getFood()).isEqualTo(5);
        assertThat(herbo.getNumber()).isEqualTo(10);
        assertThat(herbo.getKindness()).isEqualTo(8);
    }

    @Test
    void herboSetKindness() {
        Herbo herbo = new Herbo("Deer", 4, 20, 3);
        herbo.setKindness(9);
        assertThat(herbo.getKindness()).isEqualTo(9);
    }

    @Test
    void predatorCreationAndGetters() {
        Predator predator = new Predator("Lion", 12, 30);
        assertThat(predator.getName()).isEqualTo("Lion");
        assertThat(predator.getFood()).isEqualTo(12);
        assertThat(predator.getNumber()).isEqualTo(30);
    }

    @Test
    void monkeyCreation() {
        Monkey monkey = new Monkey("Abu", 3, 5, 7);
        assertThat(monkey.getName()).isEqualTo("Abu");
        assertThat(monkey.getFood()).isEqualTo(3);
        assertThat(monkey.getNumber()).isEqualTo(5);
        assertThat(monkey.getKindness()).isEqualTo(7);
    }

    @Test
    void rabbitCreation() {
        Rabbit rabbit = new Rabbit("Fluffy", 2, 15, 9);
        assertThat(rabbit.getName()).isEqualTo("Fluffy");
        assertThat(rabbit.getFood()).isEqualTo(2);
        assertThat(rabbit.getNumber()).isEqualTo(15);
        assertThat(rabbit.getKindness()).isEqualTo(9);
    }

    @Test
    void tigerCreation() {
        Tiger tiger = new Tiger("Tigger", 10, 25);
        assertThat(tiger.getName()).isEqualTo("Tigger");
        assertThat(tiger.getFood()).isEqualTo(10);
        assertThat(tiger.getNumber()).isEqualTo(25);
    }

    @Test
    void wolfCreation() {
        Wolf wolf = new Wolf("Akela", 8, 35);
        assertThat(wolf.getName()).isEqualTo("Akela");
        assertThat(wolf.getFood()).isEqualTo(8);
        assertThat(wolf.getNumber()).isEqualTo(35);
    }

    @Test
    void animalImplementsIAlive() {
        Animal animal = new Tiger("Test", 5, 1);
        animal.setFood(10);
        assertThat(animal.getFood()).isEqualTo(10);
    }

    @Test
    void animalImplementsIInventory() {
        Animal animal = new Wolf("Test", 6, 100);
        animal.setNumber(200);
        assertThat(animal.getNumber()).isEqualTo(200);
    }
}
