package hse.kpo.homework1.interfaces;

/**
 * Интерфейс для живых существ.
 * Следует принципу Interface Segregation Principle (ISP) -
 * содержит только методы, относящиеся к питанию живых существ.
 */
public interface IAlive {
    int getFood();
    void setFood(int food);
}
