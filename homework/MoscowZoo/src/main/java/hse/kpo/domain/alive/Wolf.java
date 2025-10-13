package hse.kpo.domain.alive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Wolf extends Predator {
    private String packRole;

    public Wolf(String name, int food, int number, String packRole) {
        super(name, food, number);
        this.packRole = packRole;
    }

}