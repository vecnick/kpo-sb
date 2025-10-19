package hse.kpo.homework1.service;

import hse.kpo.homework1.living.Tiger;
import hse.kpo.homework1.living.Wolf;
import hse.kpo.homework1.living.Monkey;
import hse.kpo.homework1.living.Rabbit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;

class VetClinicServiceTest {

    private VetClinicService vetClinic;

    @BeforeEach
    void setUp() {
        vetClinic = new VetClinicService();
    }

    @Test
    void alwaysAcceptsAnimal() {
        assertThat(vetClinic.checkHealth(new Tiger("Tigran", 10, 9))).isTrue();
    }

    @Test
    void acceptsWolf() {
        assertThat(vetClinic.checkHealth(new Wolf("Grey", 8, 15))).isTrue();
    }

    @Test
    void acceptsMonkey() {
        assertThat(vetClinic.checkHealth(new Monkey("Chita", 3, 20, 7))).isTrue();
    }

    @Test
    void acceptsRabbit() {
        assertThat(vetClinic.checkHealth(new Rabbit("Fluffy", 2, 25, 8))).isTrue();
    }
}
