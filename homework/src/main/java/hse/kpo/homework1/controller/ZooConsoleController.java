package hse.kpo.homework1.controller;

import hse.kpo.homework1.living.Animal;
import hse.kpo.homework1.interfaces.IInventory;
import hse.kpo.homework1.service.ZooService;
import hse.kpo.homework1.factory.AnimalRegistry;
import hse.kpo.homework1.factory.AnimalTypeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Контроллер для обработки команд консольного приложения.
 * Следует принципу Single Responsibility - отвечает только за обработку команд.
 * Вся бизнес-логика делегируется в ZooService.
 * Использует AnimalRegistry для динамического создания животных.
 */
@Component
public class ZooConsoleController {
    private final ZooService zooService;
    private final AnimalRegistry animalRegistry;

    @Autowired
    public ZooConsoleController(ZooService zooService, AnimalRegistry animalRegistry) {
        this.zooService = zooService;
        this.animalRegistry = animalRegistry;
    }

    /**
     * Создает животное с дополнительными параметрами
     */
    public Animal createAnimalWithParams(String typeIdentifier, String name, int food,
                                        int inventoryNumber, Map<String, Object> additionalParams) {
        Optional<AnimalTypeDefinition> typeDefOpt = resolveTypeDefinition(typeIdentifier);

        if (typeDefOpt.isEmpty()) {
            return null;
        }

        try {
            return typeDefOpt.get().createAnimal(name, food, inventoryNumber, additionalParams);
        } catch (Exception e) {
            System.err.println("Ошибка создания животного: " + e.getMessage());
            return null;
        }
    }

    /**
     * Определяет тип животного по строковому идентификатору (ключ или номер)
     */
    private Optional<AnimalTypeDefinition> resolveTypeDefinition(String identifier) {
        // Пробуем интерпретировать как номер
        try {
            int number = Integer.parseInt(identifier);
            return animalRegistry.getTypeInfoByNumber(number);
        } catch (NumberFormatException e) {
            // Это не номер, ищем по ключу
            return animalRegistry.getTypeInfo(identifier);
        }
    }

    /**
     * Получает определение типа животного
     */
    public Optional<AnimalTypeDefinition> getTypeDefinition(String identifier) {
        return resolveTypeDefinition(identifier);
    }

    /**
     * Добавляет животное в зоопарк
     */
    public String addAnimal(Animal animal) {
        if (animal == null) {
            return "unknown_type";
        }
        boolean added = zooService.addAnimal(animal);
        return added ? "success" : "health_check_failed";
    }

    /**
     * Возвращает общее потребление корма всеми животными
     * @return количество корма в кг/день
     */
    public int getTotalFoodConsumption() {
        return zooService.getTotalFood();
    }

    /**
     * Возвращает список животных, подходящих для контактного зоопарка
     * @return список животных
     */
    public List<Animal> getContactZooAnimals() {
        return zooService.getContactAnimals();
    }

    /**
     * Возвращает полный инвентарный отчет
     * @return список всех инвентаризируемых объектов
     */
    public List<IInventory> getInventoryReport() {
        return zooService.getInventoryReport();
    }

    /**
     * Возвращает список всех доступных типов животных
     */
    public List<AnimalTypeDefinition> getAvailableTypes() {
        return animalRegistry.getAllTypes();
    }

    /**
     * Форматирует список доступных типов для отображения
     */
    public String formatAvailableTypes() {
        StringBuilder sb = new StringBuilder("Доступные животные:\n");
        List<AnimalTypeDefinition> types = animalRegistry.getAllTypes();
        for (int i = 0; i < types.size(); i++) {
            AnimalTypeDefinition type = types.get(i);
            sb.append(String.format("%d. %s (%s)", i + 1, type.getDisplayName(), type.getKey()));
            sb.append("\n");
        }
        return sb.toString();
    }
}
