package hse.kpo.homework1.inventory;

/**
 * Конкретный класс для столов.
 * Следует принципу Liskov Substitution Principle (LSP):
 * может использоваться везде, где ожидается Thing или IInventory.
 */
public class Table extends Thing {
    public Table(String name, int number) {
        super(name, number);
    }
}
