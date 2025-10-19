package hse.kpo.homework1.living;

/**
 * Конкретный класс для обезьян (травоядное животное).
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Herbo или Animal.
 */
public class Monkey extends Herbo {
    public Monkey(String name, int food, int number, int kindness) {
        super(name, food, number, kindness);
    }
}
