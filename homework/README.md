# Zoo Management Console Application

## Описание
ERP-подобная система для Московского зоопарка. Учет животных и вещей, SOLID-архитектура, внедрение зависимостей через Spring.

## Структура кода
- `hse.kpo.homework1.living` — классы животных (иерархия, травоядные, хищники...)
- `hse.kpo.homework1.inventory` — классы-вещи (стол, компьютер, ...)
- `hse.kpo.homework1.interfaces` — интерфейсы для живых и инвентаря
- `hse.kpo.homework1.service` — сервисы Spring (ZooService, VetClinicService)
- `hse.kpo.homework1.ZooApp` — консольная точка входа и Spring Boot-стартер

## Запуск
```
./gradlew bootRun
```

## Тесты и покрытие
```
./gradlew test
./gradlew jacocoTestReport
# html-отчёт: build/reports/jacoco/test/html/index.html
```

## SOLID и DI
- **S**: каждый класс и сервис отвечает за свой кусок доменной логики
- **O**: добавлять новых животных/вещи легко через наследование
- **L**: Herbo/Predator/Animal используются взаимозаменяемо как IAlive, IInventory
- **I**: IAlive/IInventory вынесены отдельно
- **D**: сервисы получают зависимости через конструктор (@Autowired/DI)

## Пример архитектуры:
- VetClinicService — проверяет здоровье животных перед приемом
- ZooService — управляет коллекцией животных, вещей, отчетами
- Lombok — избавляет от рутины геттеров/сеттеров
- Полное покрытие unit-тестами (папка test, Jacoco report)



