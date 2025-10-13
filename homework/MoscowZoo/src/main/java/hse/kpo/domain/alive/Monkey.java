package hse.kpo.domain.alive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Monkey extends Herbo {
    private String furColor;

    public Monkey(String name, int food, int number, int kindnessLevel, String furColor) {
        super(name, food, number, kindnessLevel);
        this.furColor = furColor;
    }

}