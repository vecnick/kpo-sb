package hse.finance.domain;

import java.util.Objects;
import java.util.UUID;

public class Category implements Identifiable {
    private final UUID id;
    private final CategoryType type;
    private String name;

    public Category(UUID id, CategoryType type, String name) {
        this.id = Objects.requireNonNull(id, "id");
        this.type = Objects.requireNonNull(type, "type");
        this.name = Objects.requireNonNull(name, "name");
    }

    @Override
    public UUID getId() {
        return id;
    }

    public CategoryType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
