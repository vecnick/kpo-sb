package hse.kpo.homework1.service;

import hse.kpo.homework1.interfaces.IHealthChecker;
import hse.kpo.homework1.living.Animal;
import org.springframework.stereotype.Service;

/**
 * Сервис ветеринарной клиники.
 * Следует принципам:
 * - Single Responsibility Principle (SRP): отвечает только за проверку здоровья животных
 * - Dependency Inversion Principle (DIP): реализует интерфейс IHealthChecker,
 *   что позволяет другим модулям зависеть от абстракции
 */
@Service
public class VetClinicService implements IHealthChecker {
    @Override
    public boolean checkHealth(Animal animal) {
        // В реальном приложении — сложная логика, здесь — всегда true
        return true;
    }
}
