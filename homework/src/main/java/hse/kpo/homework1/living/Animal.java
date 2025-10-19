package hse.kpo.homework1.living;

import lombok.Getter;
import lombok.Setter;
import hse.kpo.homework1.interfaces.IAlive;
import hse.kpo.homework1.interfaces.IInventory;

/**
 * Абстрактный базовый класс для всех животных.
 * Следует принципам:
 * - Single Responsibility Principle (SRP): отвечает только за хранение данных о животном
 * - Open/Closed Principle (OCP): открыт для расширения через наследование, закрыт для модификации
 * - Liskov Substitution Principle (LSP): все наследники могут использоваться вместо Animal
 *
 * Реализует IAlive (живое существо) и IInventory (инвентаризируемый объект)
 */
@Getter
@Setter
public abstract class Animal implements IAlive, IInventory {
    private String name;
    private int food;
    private int number;

    public Animal(String name, int food, int number) {
        this.name = name;
        this.food = food;
        this.number = number;
    }
}
