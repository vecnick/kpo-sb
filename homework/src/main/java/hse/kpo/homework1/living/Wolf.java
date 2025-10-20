package hse.kpo.homework1.living;

import java.util.Map;

/**
 * Конкретный класс для волков (хищное животное).
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Predator или Animal.
 */
public class Wolf extends Predator {
    public Wolf(String name, int food, int number) {
        super(name, food, number);
    }

    public Wolf(Map<String, Object> params) {
        this(
            (String) params.get("name"),
            (Integer) params.get("food"),
            (Integer) params.get("inventoryNumber")
        );
    }
}
