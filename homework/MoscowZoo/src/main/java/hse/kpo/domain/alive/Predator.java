package hse.kpo.domain.alive;

public abstract class Predator extends Animal {
    public Predator(String name, int food, int number) {
        super(name, food, number);
    }

    @Override
    public boolean canBeInContactZoo() {
        return false;
    }
}