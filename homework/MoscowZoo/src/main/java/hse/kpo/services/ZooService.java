package hse.kpo.services;

import hse.kpo.domain.alive.Animal;
import hse.kpo.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZooService {
    private final VeterinaryClinic veterinaryClinic;
    private final List<Animal> animals;
    private final List<IInventory> inventory;

    @Autowired
    public ZooService(VeterinaryClinic veterinaryClinic) {
        this.veterinaryClinic = veterinaryClinic;
        this.animals = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    public boolean addAnimal(Animal animal) {
        if (veterinaryClinic.checkHealth(animal)) {
            animal.setHealthy(true);
            animals.add(animal);
            inventory.add(animal);
            System.out.println("Animal added successfully " +  animal.getName());
            return true;
        } else {
            System.out.println("Cannot accept animal " + animal.getName());
            return false;
        }
    }

    public int getTotalFoodConsumption() {
        return animals.stream()
                .mapToInt(Animal::getFood)
                .sum();
    }

    public List<Animal> getAnimalsForContactZoo() {
        return animals.stream()
                .filter(Animal::canBeInContactZoo)
                .collect(Collectors.toList());
    }

    public List<Animal> getAllAnimals() {
        return new ArrayList<>(animals);
    }

    public List<IInventory> getAllInventory() {
        return new ArrayList<>(inventory);
    }

    public void addInventoryItem(IInventory item) {
        inventory.add(item);
        System.out.println(" Item added: " + item.getName() + " (id = " + item.getNumber() + ")");
    }

    public int getAnimalCount() {
        return animals.size();
    }
}