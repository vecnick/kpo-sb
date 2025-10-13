package hse.kpo.domain.alive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Rabbit extends Herbo {
    private int earLength;

    public Rabbit(String name, int food, int number, int kindnessLevel, int earLength) {
        super(name, food, number, kindnessLevel);
        this.earLength = earLength;
    }

}