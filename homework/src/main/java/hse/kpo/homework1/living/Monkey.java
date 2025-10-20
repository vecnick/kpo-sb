package hse.kpo.homework1.living;

import java.util.Map;

/**
 * Конкретный класс для обезьян (травоядное животное).
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Herbo или Animal.
 */
public class Monkey extends Herbo {
    public Monkey(String name, int food, int number, int kindness) {
        super(name, food, number, kindness);
    }

    public Monkey(Map<String, Object> params) {
        this(
            (String) params.get("name"),
            (Integer) params.get("food"),
            (Integer) params.get("inventoryNumber"),
            (Integer) params.get("kindness")
        );
    }
}
