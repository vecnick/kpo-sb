# Finance App — модуль «Учет финансов»

Коротко: консольное приложение для учёта финансов (счета, категории, операции), с аналитикой, импортом/экспортом и расчётом баланса. Архитектура слоями: домен → репозитории → фасады/сервисы → команды (CLI). Зависимости собираются в `AppContext` (простой DI-контейнер).

## Быстрый старт

- Сборка и запуск демо-сценария:

```bat
cd D:\JavaProjects\kpo-sb\homework\finance
gradlew.bat clean build run --no-daemon --console=plain
```

- Где появится экспорт: после демо будет создан файл

```
D:\JavaProjects\kpo-sb\homework\finance\build\exports\finance-data.csv
```

- Импорт из CSV и пересчёт баланса:

```bat
cd D:\JavaProjects\kpo-sb\homework\finance
rem пример — импорт только что экспортированного CSV
gradlew.bat run --args="import build\exports\finance-data.csv csv" --no-daemon --console=plain
```

## Что реализовано

- CRUD по доменным сущностям:
  - BankAccount (счёт)
  - Category (категория, тип INCOME/EXPENSE)
  - Operation (операция, тип INCOME/EXPENSE)
- Аналитика:
  - Разница доходов и расходов за период
  - Группировка сумм по категориям за период
  - Доп.: средний чек расходов по категории
- Импорт/экспорт:
  - Экспорт всех данных в CSV/JSON/YAML
  - Импорт из CSV (JSON/YAML легко добавить аналогично)
- Управление данными:
  - Пересчёт баланса всех счетов по операциям
- Статистика:
  - Измерение времени выполнения команд

## Где что лежит (структура пакетов)

- `hse.finance.domain` — доменные сущности и фабрика создания (`Factory`)
- `hse.finance.repository` — интерфейсы и in‑memory реализации репозиториев
- `hse.finance.facade` — фасады по агрегатам и аналитика (`AnalyticsFacade`)
- `hse.finance.service` — сервисы импорта/экспорта/пересчёта (`ImportService`, `ExportService`, `BalanceService`)
- `hse.finance.importing` — абстрактный импортёр и CSV‑импортёр
- `hse.finance.export` — экспорт (CSV/JSON/YAML) и агрегатор данных (`FinanceData`)
- `hse.finance.command` — команды пользовательских сценариев и декоратор времени
- `hse.finance.app` — `AppContext` (DI) и `Main` (точка входа)

## Экспорт: формат и открытие

- Путь к файлу после демо: `finance/build/exports/finance-data.csv`
- Разделитель столбцов: `;`
- Заголовки секций: `#ACCOUNTS`, `#CATEGORIES`, `#OPERATIONS`
- Кодировка: UTF‑8


## Архитектура и паттерны

- Facade — `BankAccountFacade`, `CategoryFacade`, `OperationFacade`, `AnalyticsFacade` скрывают детали работы с репозиториями и агрегируют сценарии.
- Command — каждая операция приложения оформлена как команда в `hse.finance.command.*` (создание/листинг/аналитика/импорт/экспорт/пересчёт).
- Decorator — `TimeMeasuredCommandDecorator` измеряет время выполнения любой команды.
- Template Method — `AbstractImporter` + `CSVImporter` задают общий конвейер импорта, отличается только парсинг.
- Visitor — экспорт через `FinanceData.accept(visitor)`: `CSVExportVisitor`/`JSONExportVisitor`/`YAMLExportVisitor`.
- Factory — `hse.finance.domain.Factory` создаёт сущности с валидацией.
- Repository — интерфейсы доступа к данным с in‑memory реализациями, легко заменить на БД.
- DI (контейнер/реестр) — `AppContext` как простой composition root, собирает зависимости.

## SOLID и GRASP (кратко)

- SRP — классы отвечают за свою зону (фасад, сервис, команда и т.д.).
- OCP — новые форматы/команды добавляются без изменения существующего кода (через новые реализации).
- LSP — подменяемые реализации репозиториев (in‑memory ↔ реальная БД) и сервисов.
- ISP — узкие интерфейсы (репозиторий, фасад, команда).
- DIP — высокий уровень зависит от абстракций, внедрение через `AppContext`.
- GRASP: Information Expert (аналитика в `AnalyticsFacade`), Controller (`Main`), Low Coupling/High Cohesion, Pure Fabrication (декоратор времени).
