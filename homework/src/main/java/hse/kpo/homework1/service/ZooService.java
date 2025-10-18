package hse.kpo.homework1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hse.kpo.homework1.living.Animal;
import hse.kpo.homework1.living.Herbo;
import hse.kpo.homework1.interfaces.IInventory;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZooService {
    private final VetClinicService vetClinicService;
    private final List<Animal> animals = new ArrayList<>();
    private final List<IInventory> inventory = new ArrayList<>();

    @Autowired
    public ZooService(VetClinicService vetClinicService) {
        this.vetClinicService = vetClinicService;
    }

    public boolean addAnimal(Animal animal) {
        if (vetClinicService.checkHealth(animal)) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    public int getTotalFood() {
        return animals.stream().mapToInt(Animal::getFood).sum();
    }

    public List<Animal> getContactAnimals() {
        List<Animal> result = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal instanceof Herbo herbo && herbo.getKindness() > 5) {
                result.add(animal);
            }
        }
        return result;
    }

    public void addThing(IInventory thing) {
        inventory.add(thing);
    }

    public List<IInventory> getInventoryReport() {
        List<IInventory> report = new ArrayList<>();
        report.addAll(inventory);
        report.addAll(animals); // животные — тоже инвентарь
        return report;
    }
}
