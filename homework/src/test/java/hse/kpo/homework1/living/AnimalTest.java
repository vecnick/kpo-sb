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
}
