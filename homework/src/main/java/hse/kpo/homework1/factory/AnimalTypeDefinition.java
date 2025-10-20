package hse.kpo.homework1.factory;

import hse.kpo.homework1.living.Animal;
import lombok.Getter;

import java.util.*;

/**
 * Полное описание типа животного с произвольными параметрами.
 * Следует принципу Single Responsibility - хранит всю информацию о типе животного.
 */
@Getter
public class AnimalTypeDefinition {
    private final String key;
    private final String displayName;
    private final List<AnimalParameter> additionalParameters;
    private final FlexibleAnimalFactory factory;

    public AnimalTypeDefinition(String key, String displayName,
                               List<AnimalParameter> additionalParameters,
                               FlexibleAnimalFactory factory) {
        this.key = key;
        this.displayName = displayName;
        this.additionalParameters = Collections.unmodifiableList(additionalParameters);
        this.factory = factory;
    }

    /**
     * Создает животное с заданными параметрами
     */
    public Animal createAnimal(String name, int food, int inventoryNumber, Map<String, Object> additionalParams) {
        Map<String, Object> baseParams = new HashMap<>();
        baseParams.put("name", name);
        baseParams.put("food", food);
        baseParams.put("inventoryNumber", inventoryNumber);

        // Заполняем дефолтными значениями, если параметр не передан
        Map<String, Object> fullAdditionalParams = new HashMap<>();
        for (AnimalParameter param : additionalParameters) {
            Object value = additionalParams.getOrDefault(param.getName(), param.getDefaultValue());
            if (param.isRequired() && value == null) {
                throw new IllegalArgumentException("Required parameter '" + param.getName() + "' is missing");
            }
            fullAdditionalParams.put(param.getName(), value);
        }

        return factory.create(baseParams, fullAdditionalParams);
    }

    /**
     * Получает параметр по имени
     */
    public Optional<AnimalParameter> getParameter(String name) {
        return additionalParameters.stream()
            .filter(p -> p.getName().equalsIgnoreCase(name))
            .findFirst();
    }
}