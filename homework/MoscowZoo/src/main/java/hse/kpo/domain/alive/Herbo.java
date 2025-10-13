package hse.kpo.domain.alive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Herbo extends Animal {
    private int kindnessLevel;

    public Herbo(String name, int food, int number, int kindnessLevel) {
        super(name, food, number);
        this.kindnessLevel = kindnessLevel;
    }

    @Override
    public boolean canBeInContactZoo() {
        return isHealthy() && kindnessLevel > 5;
    }
}