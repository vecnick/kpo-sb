package hse.kpo.homework1.living;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Herbo extends Animal {
    private int kindness;
    public Herbo(String name, int food, int number, int kindness) {
        super(name, food, number);
        this.kindness = kindness;
    }
}
