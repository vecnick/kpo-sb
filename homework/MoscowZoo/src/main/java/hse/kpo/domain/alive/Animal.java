package hse.kpo.domain.alive;

import hse.kpo.interfaces.*;
import lombok.Getter;
import lombok.Setter;

public abstract class Animal implements IAlive, IInventory {

    @Setter
    private String name;
    private int food;
    private int number;

    @Setter
    @Getter
    private boolean healthy;

    public Animal(String name, int food, int number) {
        this.name = name;
        this.food = food;
        this.number = number;
        this.healthy = false;
    }

    @Override
    public int getFood() {
        return food;
    }

    @Override
    public void setFood(int food) {
        this.food = food;
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

    public abstract boolean canBeInContactZoo();
}