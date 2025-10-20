package hse.kpo.homework1.factory;

import hse.kpo.homework1.living.Animal;

import java.util.Map;

/**
 * Фабрика для создания животных с произвольным набором параметров.
 * Следует принципу Open/Closed - можно расширять без модификации.
 */
@FunctionalInterface
public interface FlexibleAnimalFactory {
    /**
     * Создает животное с заданными параметрами
     * @param baseParams базовые параметры (name, food, inventoryNumber)
     * @param additionalParams дополнительные параметры (kindness, агрессивность, и т.д.)
     * @return созданное животное
     */
    Animal create(Map<String, Object> baseParams, Map<String, Object> additionalParams);
}

