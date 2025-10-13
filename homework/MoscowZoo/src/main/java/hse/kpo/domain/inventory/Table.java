package hse.kpo.domain.inventory;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Table extends Thing {
    private String material;

    public Table(String name, int number, String material) {
        super(name, number);
        this.material = material;
    }

}