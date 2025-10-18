package hse.kpo.homework1.living;

import lombok.Getter;
import lombok.Setter;
import hse.kpo.homework1.interfaces.IAlive;
import hse.kpo.homework1.interfaces.IInventory;

@Getter
@Setter
public abstract class Animal implements IAlive, IInventory {
    private String name;
    private int food;
    private int number;
    public Animal(String name, int food, int number) {
        this.name = name;
        this.food = food;
        this.number = number;
    }
}
