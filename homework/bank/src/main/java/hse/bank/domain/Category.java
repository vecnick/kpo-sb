package hse.bank.domain;

import hse.bank.io.export.DataVisitor;
import hse.bank.io.export.Visitable;
import hse.bank.enums.OperationType;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Category implements Visitable {
    private final Long id;
    private final OperationType type;

    @Setter
    private String name;

    @Override
    public void accept(DataVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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