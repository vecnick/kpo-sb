package hse.kpo.domain.inventory;

import hse.kpo.interfaces.IInventory;
import lombok.Setter;

public abstract class Thing implements IInventory {

    @Setter
    private String name;
    private int number;

    public Thing(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String getName() {
        return name;
    }

}