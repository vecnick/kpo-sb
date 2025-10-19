package hse.kpo.homework1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hse.kpo.homework1.interfaces.IHealthChecker;
import hse.kpo.homework1.living.Animal;
import hse.kpo.homework1.living.Herbo;
import hse.kpo.homework1.interfaces.IInventory;
import java.util.ArrayList;
import java.util.List;

/**
 * Основной сервис зоопарка.
 * Следует принципам:
 * - Single Responsibility Principle (SRP): отвечает за управление животными и инвентарем зоопарка
 * - Open/Closed Principle (OCP): можно расширять функциональность без изменения существующего кода
 * - Dependency Inversion Principle (DIP): зависит от абстракции IHealthChecker, а не от конкретной реализации
 *
 * Использует Dependency Injection через конструктор для внедрения зависимостей (Spring DI Container).
 */
@Service
public class ZooService {
    private final IHealthChecker healthChecker;
    private final List<Animal> animals = new ArrayList<>();
    private final List<IInventory> inventory = new ArrayList<>();

    @Autowired
    public ZooService(IHealthChecker healthChecker) {
        this.healthChecker = healthChecker;
    }

    /**
     * Добавляет животное в зоопарк после проверки здоровья
     * @param animal животное для добавления
     * @return true если животное добавлено, false если не прошло проверку здоровья
     */
    public boolean addAnimal(Animal animal) {
        if (healthChecker.checkHealth(animal)) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    /**
     * Вычисляет общее количество корма для всех животных
     * @return количество корма в кг/день
     */
    public int getTotalFood() {
        return animals.stream().mapToInt(Animal::getFood).sum();
    }

    /**
     * Возвращает список животных, подходящих для контактного зоопарка
     * (травоядные с добротой выше 5)
     * @return список животных
     */
    public List<Animal> getContactAnimals() {
        List<Animal> result = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal instanceof Herbo herbo && herbo.getKindness() > 5) {
                result.add(animal);
            }
        }
        return result;
    }

    /**
     * Добавляет предмет в инвентарь зоопарка
     * @param thing предмет для добавления
     */
    public void addThing(IInventory thing) {
        inventory.add(thing);
    }

    /**
     * Формирует отчет по всему инвентарю (предметы и животные)
     * @return список всех инвентаризируемых объектов
     */
    public List<IInventory> getInventoryReport() {
        List<IInventory> report = new ArrayList<>();
        report.addAll(inventory);
        report.addAll(animals); // животные — тоже инвентарь
        return report;
    }
}
