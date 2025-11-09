# Запуск приложения

- Для запуска требуется IntelliJ IDEA и gradle

1) Скачайте gradle

2) Запустите gradlew.bat (Windows) или ./gradlew (Unix)

3) Запустите программу в IntelliJ IDEA

# Общая идея решения

### Реализована система управления банковскими операциями с поддержкой следующих функций:

1) Управление банковскими счетами (создание, удаление)

2) Управление категориями операций

3) Выполнение и удаление финансовых операций (доходы/расходы)

4) Импорт/экспорт данных в JSON формате

5) Генерация аналитических отчетов

6) Командный интерфейс с возможностью декорирования команд

# Реализованные принципы SOLID и GRASP

### SOLID принципы:
1) SRP (Принцип единственной ответственности)

- BankAccountRepositoryImpl, CategoryRepositoryImpl, OperationRepositoryImpl - отвечают только за работу с данными

- Сервисы (AccountService, CategoryService и т.д.) - содержат бизнес-логику

- Команды - инкапсулируют отдельные действия

2) OCP (Принцип открытости/закрытости)

- TimingCommandDecorator - расширяет функциональность команд без их модификации

- Система экспорта через Visitor позволяет добавлять новые форматы

3) LSP (Принцип подстановки Лисков)

- Все команды наследуются от базового Command

4) ISP (Принцип разделения интерфейсов)

- Раздельные интерфейсы сервисов и репозиториев

- Специализированные DTO для разных операций

5) DIP (Принцип инверсии зависимостей)

- Сервисы зависят от абстракций репозиториев

- Фасад зависит от абстракций сервисов

### GRASP принципы:
1) Information Expert - репозитории владеют информацией о сущностях

2) Creator - DomainFactory создает доменные объекты

3) Controller - FinanceFacade управляет приложением

4) Low Coupling - слабая связанность через интерфейсы

5) High Cohesion - каждый класс имеет четкую зону ответственности

# Реализованные паттерны GoF
1) Command (Команда) \
   Классы: Command, AddOperationCommand, CreateAccountCommand и др.

2) Facade (Фасад) \
   Классы: FinanceFacade

3) Visitor (Посетитель) \
   Классы: DataVisitor, JsonExportVisitor, Visitable

4) Factory (Фабрика) \
   Классы: DomainFactory

5) Decorator (Декоратор) \
   Классы: TimingCommandDecorator

6) Repository (Репозиторий) \
   Классы: BankAccountRepository, CategoryRepository, OperationRepository и их реализации

7) DTO (Data Transfer Object) \
   Классы: CreateAccountRequest, CreateOperationRequest и др.

8) Strategy (Стратегия) \
   Классы: AbstractDataImporter, JsonDataImporter

## DI-контейнер

- В проекте реализован кастомный DI контейнер через класс 
  ApplicationContext, который выполняет роль IoC-контейнера для 
  управления зависимостями между компонентами.