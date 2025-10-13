package hse.kpo.services;

import hse.kpo.domain.alive.Animal;
import org.springframework.stereotype.Service;

@Service
public class VeterinaryClinic {

    public boolean checkHealth(Animal animal) {
        System.out.println("Health chacking: " + animal.getName());

        boolean isHealthy = animal.getName().length() != 5;

        if (isHealthy) {
            System.out.println(animal.getName() + " is healthy");
        } else {
            System.out.println(animal.getName() + " isn't healthy");
        }

        return isHealthy;
    }
}