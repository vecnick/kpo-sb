package hse.kpo.homework1.factory;

import lombok.Getter;

/**
 * Описание параметра животного.
 * Следует принципу Single Responsibility - хранит метаданные об одном параметре.
 */
@Getter
public class AnimalParameter {
    private final String name;
    private final String displayName;
    private final Class<?> type;
    private final boolean required;
    private final Object defaultValue;

    public AnimalParameter(String name, String displayName, Class<?> type, boolean required, Object defaultValue) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    public static AnimalParameter required(String name, String displayName, Class<?> type) {
        return new AnimalParameter(name, displayName, type, true, null);
    }

    public static AnimalParameter optional(String name, String displayName, Class<?> type, Object defaultValue) {
        return new AnimalParameter(name, displayName, type, false, defaultValue);
    }

    /**
     * Парсит значение из строки в нужный тип
     */
    public Object parseValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }

        if (type == Integer.class || type == int.class) {
            return Integer.parseInt(value.trim());
        } else if (type == String.class) {
            return value;
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(value.trim());
        } else if (type == Double.class || type == double.class) {
            return Double.parseDouble(value.trim());
        }

        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}

