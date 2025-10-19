package hse.kpo.homework1.living;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс для травоядных животных.
 * Следует принципам:
 * - Single Responsibility Principle (SRP): отвечает только за хранение данных о травоядных
 * - Open/Closed Principle (OCP): можно расширять, создавая конкретные виды травоядных
 * - Liskov Substitution Principle (LSP): может использоваться везде, где ожидается Animal
 */
@Getter
@Setter
public class Herbo extends Animal {
    private int kindness;

    public Herbo(String name, int food, int number, int kindness) {
        super(name, food, number);
        this.kindness = kindness;
    }
}
