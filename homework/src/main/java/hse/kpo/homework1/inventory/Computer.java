package hse.kpo.homework1.inventory;

/**
 * Конкретный класс для компьютеров.
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Thing или IInventory.
 */
public class Computer extends Thing {
    public Computer(String name, int number) {
        super(name, number);
    }
}
