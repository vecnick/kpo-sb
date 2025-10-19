package hse.kpo.homework1.living;

/**
 * Класс для хищных животных.
 * Следует принципам:
 * - Single Responsibility Principle (SRP): отвечает только за хранение данных о хищниках
 * - Open/Closed Principle (OCP): можно расширять, создавая конкретные виды хищников
 * - Liskov Substitution Principle (LSP): может использоваться везде, где ожидается Animal
 */
public class Predator extends Animal {
    public Predator(String name, int food, int number) {
        super(name, food, number);
    }
}
