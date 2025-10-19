package hse.kpo.homework1.living;

/**
 * Конкретный класс для волков (хищное животное).
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Predator или Animal.
 */
public class Wolf extends Predator {
    public Wolf(String name, int food, int number) {
        super(name, food, number);
    }
}
