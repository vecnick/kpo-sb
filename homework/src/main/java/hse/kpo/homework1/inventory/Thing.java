package hse.kpo.homework1.inventory;

import lombok.Getter;
import lombok.Setter;
import hse.kpo.homework1.interfaces.IInventory;

@Getter
@Setter
public class Thing implements IInventory {
    private String name;
    private int number;
    public Thing(String name, int number) {
        this.name = name;
        this.number = number;
    }
}
