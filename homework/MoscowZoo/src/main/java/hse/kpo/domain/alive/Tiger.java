package hse.kpo.domain.alive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tiger extends Predator {
    private int stripeCount;

    public Tiger(String name, int food, int number, int stripeCount) {
        super(name, food, number);
        this.stripeCount = stripeCount;
    }

}