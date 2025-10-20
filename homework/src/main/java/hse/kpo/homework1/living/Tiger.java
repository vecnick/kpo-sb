package hse.kpo.homework1.living;

import java.util.Map;

/**
 * Конкретный класс для тигров (хищное животное).
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Predator или Animal.
 */
public class Tiger extends Predator {
    public Tiger(String name, int food, int number) {
        super(name, food, number);
    }

    public Tiger(Map<String, Object> params) {
        this(
            (String) params.get("name"),
            (Integer) params.get("food"),
            (Integer) params.get("inventoryNumber")
        );
    }
}
