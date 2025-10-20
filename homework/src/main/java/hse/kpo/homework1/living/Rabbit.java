package hse.kpo.homework1.living;

import java.util.Map;

/**
 * Конкретный класс для кроликов (травоядное животное).
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Herbo или Animal.
 */
public class Rabbit extends Herbo {
    public Rabbit(String name, int food, int number, int kindness) {
        super(name, food, number, kindness);
    }

    public Rabbit(Map<String, Object> params) {
        this(
            (String) params.get("name"),
            (Integer) params.get("food"),
            (Integer) params.get("inventoryNumber"),
            (Integer) params.get("kindness")
        );
    }
}
