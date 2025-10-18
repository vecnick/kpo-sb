package hse.kpo.homework1.service;

import hse.kpo.homework1.living.Animal;
import org.springframework.stereotype.Service;

@Service
public class VetClinicService {
    public boolean checkHealth(Animal animal) {
        // В реальном приложении — сложная логика, здесь — всегда true
        return true;
    }
}
