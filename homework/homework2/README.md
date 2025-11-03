# HSE-Bank Finances Module


Проект демонстрирует реализацию доменной модели модуля "Учет финансов" на Java с акцентом на SOLID, GRASP и набор паттернов проектирования.


Запуск


1. Скомпилировать:
   javac -d out $(find src -name "*.java")
2. Запустить:
   java -cp out com.hsebank.Main


Реализовано


- Доменные классы: BankAccount, Category, Operation
- Factory для создания объектов с валидацией
- Репозитории (in-memory)
- Сервисы/Facade для работы с сущностями
- Command для пользовательских сценариев
- Decorator для измерения времени выполнения команд
- Template Method для импорта (CSV/JSON)
- Visitor для экспорта в CSV
- Легковесный DI-контейнер для сборки зависимостей


Отчет по SOLID/GRASP


SRP: каждый класс имеет одну ответственность
OCP: сервисы используют интерфейсы репозиториев
LSP: наследование минимально, используются композируемые интерфейсы
ISP: интерфейсы разделены по ролям
DIP: зависимости инжектируются через конструкторы и контейнер


Паттерны


- Factory, Repository, Facade, Command, Decorator, Template Method, Visitor, Proxy (in-memory as simple cache), DI Container