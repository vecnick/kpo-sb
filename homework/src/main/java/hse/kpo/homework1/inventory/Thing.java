package hse.kpo.homework1.inventory;

import lombok.Getter;
import lombok.Setter;
import hse.kpo.homework1.interfaces.IInventory;

/**
 * Базовый класс для инвентарных предметов.
 * Следует принципам:
 * - Single Responsibility Principle (SRP): отвечает только за хранение данных о предмете
 * - Open/Closed Principle (OCP): открыт для расширения через наследование
 * - Liskov Substitution Principle (LSP): все наследники могут использоваться вместо Thing
 */
@Getter
@Setter
public class Thing implements IInventory {
    private String name;
    private int number;

    public Thing(String name, int number) {
        this.name = name;
        this.number = number;
    }
}
