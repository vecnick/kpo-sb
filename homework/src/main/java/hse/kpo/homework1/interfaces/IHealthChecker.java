package hse.kpo.homework1.interfaces;

import hse.kpo.homework1.living.Animal;

/**
 * Интерфейс для проверки здоровья животных.
 * Следует принципам:
 * - Interface Segregation Principle (ISP): содержит только один специфичный метод
 * - Dependency Inversion Principle (DIP): высокоуровневые модули (ZooService) 
 *   зависят от абстракции, а не от конкретной реализации
 */
public interface IHealthChecker {
    /**
     * Проверяет здоровье животного
     * @param animal животное для проверки
     * @return true если животное здорово, false иначе
     */
    boolean checkHealth(Animal animal);
}

