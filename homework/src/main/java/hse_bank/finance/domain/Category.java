package hse_bank.finance.domain;

import java.util.UUID;

public class Category {
    private final UUID id;
    private CategoryType type;
    private String name;

    public Category(UUID id, CategoryType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public CategoryType getType() { return type; }
    public void setType(CategoryType type) { this.type = type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}