package hse.kpo.homework1.factory;

import hse.kpo.homework1.living.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Регистр всех доступных типов животных.
 * Следует принципам:
 * - Single Responsibility Principle: управляет регистрацией и получением информации о типах животных
 * - Open/Closed Principle: открыт для расширения (добавление новых типов), закрыт для модификации
 * - Dependency Inversion Principle: использует абстракцию FlexibleAnimalFactory
 *
 * При добавлении нового вида животного достаточно добавить одну строчку в метод инициализации.
 */
@Component
public class AnimalRegistry {
    private final Map<String, AnimalTypeDefinition> typesByKey = new LinkedHashMap<>();
    private final List<AnimalTypeDefinition> types = new ArrayList<>();

    public AnimalRegistry() {
        registerHerbivore("monkey", "Обезьяна", Monkey::new);
        registerHerbivore("rabbit", "Кролик", Rabbit::new);

        registerPredator("tiger", "Тигр", Tiger::new);
        registerPredator("wolf", "Волк", Wolf::new);
    }

    private void registerHerbivore(String key, String displayName,
                                   java.util.function.Function<Map<String, Object>, Herbo> constructor) {
        List<AnimalParameter> params = List.of(
                AnimalParameter.required("kindness", "Доброта (0-10)", Integer.class)
        );

        FlexibleAnimalFactory factory = (base, add) -> {
            Map<String, Object> allParams = new HashMap<>(base);
            allParams.putAll(add);
            return constructor.apply(allParams);
        };

        register(key, displayName, params, factory);
    }

    private void registerPredator(String key, String displayName,
                                  java.util.function.Function<Map<String, Object>, Predator> constructor) {
        register(key, displayName, Collections.emptyList(), (base, add) -> constructor.apply(base));
    }

    /**
     * Регистрирует новый тип животного
     */
    private void register(String key, String displayName,
                         List<AnimalParameter> additionalParams,
                         FlexibleAnimalFactory factory) {
        AnimalTypeDefinition def = new AnimalTypeDefinition(key, displayName, additionalParams, factory);
        typesByKey.put(key.toLowerCase(), def);
        types.add(def);
    }

    /**
     * Получает информацию о типе животного по ключу
     */
    public Optional<AnimalTypeDefinition> getTypeInfo(String key) {
        return Optional.ofNullable(typesByKey.get(key.toLowerCase()));
    }

    /**
     * Получает информацию о типе животного по номеру
     */
    public Optional<AnimalTypeDefinition> getTypeInfoByNumber(int number) {
        if (number > 0 && number <= types.size()) {
            return Optional.of(types.get(number - 1));
        }
        return Optional.empty();
    }

    /**
     * Возвращает список всех зарегистрированных типов
     */
    public List<AnimalTypeDefinition> getAllTypes() {
        return Collections.unmodifiableList(types);
    }

    /**
     * Возвращает количество зарегистрированных типов
     */
    public int getTypeCount() {
        return types.size();
    }
}
