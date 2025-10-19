package hse.kpo.homework1.living;

/**
 * Конкретный класс для тигров (хищное животное).
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Predator или Animal.
 */
public class Tiger extends Predator {
    public Tiger(String name, int food, int number) {
        super(name, food, number);
    }
}
