# ERP-система для Московского зоопарка

Консольное приложение для автоматизации учета животных и инвентаря Московского зоопарка.
Система позволяет вести учет животных, проверять их здоровье перед приемом, рассчитывать
потребность в корме и формировать списки животных для контактного зоопарка.

## Инструкция по запуску

### Требования
- Java 17 или выше
- Gradle 7.0 или выше
- 
### Запуск приложения
```bash
cd homework
./gradlew build
./gradlew bootRun
```

## Применение принципов SOLID

### 1. Single Responsibility Principle (SRP)

Каждый класс отвечает за одну задачу:

- **`ZooService`** - управление коллекцией животных и инвентаря
- **`VetClinicService`** - проверка здоровья животных
- **`ZooConsoleController`** - обработка пользовательских команд
- **`AnimalRegistry`** - регистрация и создание типов животных
- **`CommandRegistry`** - управление командами приложения
- **`Animal`, `Herbo`, `Predator`** - хранение данных о животных
- **Конкретные команды** (`AddAnimalCommand`, `FoodReportCommand`) - каждая отвечает за одно действие

### 2. Open/Closed Principle (OCP)

Система открыта для расширения, но закрыта для модификации:
- Новые типы животных добавляются через `AnimalRegistry` без изменения существующего кода
- Новые команды добавляются в `CommandRegistry` без модификации `ZooConsoleController`
- Новые проверки здоровья можно добавить через реализацию `IHealthChecker`
- Абстрактные классы `Animal`, `Herbo`, `Predator` позволяют создавать новые виды животных через наследование

### 3. Liskov Substitution Principle (LSP)

Все наследники могут использоваться вместо базовых классов:
- `Monkey`, `Rabbit` используются вместо `Herbo` или `Animal`
- `Tiger`, `Wolf` используются вместо `Predator` или `Animal`
- Все команды взаимозаменяемы через интерфейс `ConsoleCommand`

### 4. Interface Segregation Principle (ISP)

Интерфейсы разделены по назначению:
- **`IAlive`** - для живых существ
- **`IInventory`** - для инвентаризируемых объектов
- **`IHealthChecker`** - для проверки здоровья
- **`ConsoleCommand`** - для команд консоли
- **`FlexibleAnimalFactory`** - для создания животных

Классы реализуют только те интерфейсы, которые им нужны.

### 5. Dependency Inversion Principle (DIP)

Зависимости от абстракций, а не от конкретных реализаций:
- `ZooService` зависит от `IHealthChecker`, а не от `VetClinicService`
- `AnimalRegistry` использует `FlexibleAnimalFactory`
- `CommandRegistry` работает с `ConsoleCommand`, а не с конкретными командами
- Все зависимости внедряются через Spring DI Container

## Паттерны проектирования

### Factory Pattern
`AnimalRegistry` и `AnimalTypeDefinition` реализуют фабричный паттерн для создания животных с произвольными параметрами.

### Command Pattern
`ConsoleCommand` и его реализации (`AddAnimalCommand`, `FoodReportCommand`, etc.) инкапсулируют команды как объекты.

### Registry Pattern
`AnimalRegistry` и `CommandRegistry` обеспечивают централизованное управление типами животных и командами.

### Dependency Injection
Spring контейнер управляет всеми зависимостями через `@Autowired` и `@Component`.

## Структура проекта

```
src/main/java/hse/kpo/homework1/
├── ZooApp.java                          # Главный класс приложения
├── command/                             
│   ├── ConsoleCommand.java              # Интерфейс команды
│   ├── CommandRegistry.java             # Регистр команд
│   └── impl/                            # Конкретные команды
│       ├── AddAnimalCommand.java
│       ├── FoodReportCommand.java
│       ├── ContactZooCommand.java
│       ├── InventoryReportCommand.java
│       └── ExitCommand.java
├── controller/
│   └── ZooConsoleController.java        # Контроллер команд
├── factory/                             
│   ├── FlexibleAnimalFactory.java       # Фабрика
│   ├── AnimalParameter.java             # Описание параметра
│   ├── AnimalTypeDefinition.java        # Определение типа
│   └── AnimalRegistry.java              # Регистр типов животных
├── interfaces/
│   ├── IAlive.java                      # Интерфейс живого существа
│   ├── IHealthChecker.java              # Интерфейс проверки здоровья
│   └── IInventory.java                  # Интерфейс инвентаря
├── inventory/
│   ├── Thing.java                       # Базовый класс предметов
│   ├── Table.java                       # Стол
│   └── Computer.java                    # Компьютер
├── living/
│   ├── Animal.java                      # Базовый класс животных
│   ├── Herbo.java                       # Травоядные
│   ├── Predator.java                    # Хищники
│   ├── Monkey.java                      # Обезьяна
│   ├── Rabbit.java                      # Кролик
│   ├── Tiger.java                       # Тигр
│   └── Wolf.java                        # Волк
└── service/
    ├── ZooService.java                  # Сервис зоопарка
    └── VetClinicService.java            # Ветеринарная клиника
```

## Тестирование

Проект покрыт unit-тестами с использованием JUnit 5 и Mockito:

- `AnimalTest` - тесты классов животных
- `ThingTest` - тесты предметов
- `VetClinicServiceTest` - тесты ветклиники
- `ZooServiceTest` - тесты сервиса зоопарка
- `ZooConsoleControllerTest` - тесты контроллера
- `AnimalRegistryTest` - тесты регистра животных

Запуск тестов с отчетом о покрытии:
```bash
./gradlew test jacocoTestReport
```

Отчет доступен в: `build/reports/jacoco/test/html/index.html`