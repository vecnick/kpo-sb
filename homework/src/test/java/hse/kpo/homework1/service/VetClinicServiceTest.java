package hse.kpo.homework1.service;

import hse.kpo.homework1.living.Tiger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class VetClinicServiceTest {
    @Test
    void alwaysAcceptsAnimal() {
        VetClinicService vetClinic = new VetClinicService();
        assertThat(vetClinic.checkHealth(new Tiger("Tigran", 10, 9))).isTrue();
    }
}
