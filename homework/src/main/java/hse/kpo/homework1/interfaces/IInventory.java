package hse.kpo.homework1.interfaces;

/**
 * Интерфейс для инвентаризируемых объектов.
 * Следует принципу Interface Segregation Principle (ISP) -
 * содержит только методы, необходимые для инвентаризации.
 */
public interface IInventory {
    int getNumber();
    void setNumber(int number);
}
