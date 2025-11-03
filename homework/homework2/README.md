# Gradle / IntelliJ Instructions


Я добавил `build.gradle`, `settings.gradle` и небольшие настройки в проект. Чтобы запускать проект через **Gradle Wrapper** и удобно работать в **IntelliJ IDEA**, следуйте шагам ниже.


## 1) Сгенерировать Gradle Wrapper (если его нет)


Если у вас установлен Gradle локально, выполните в корне проекта:


```bash
gradle wrapper --gradle-version 8.4
```


Если Gradle не установлен, сделайте одно из:
- Откройте проект в IntelliJ — IDEA предложит синхронизировать Gradle и/или создать wrapper.
- Или установите Gradle временно и выполните команду выше.


> После успешного создания в проекте появятся: `gradlew`, `gradlew.bat` и папка `gradle/wrapper/`.


## 2) Запуск через wrapper


Unix / macOS:
```bash
./gradlew build
./gradlew run
```


Windows (Powershell / cmd):
```powershell
gradlew.bat build
gradlew.bat run
```


Команда `run` использует плагин `application` и точку входа `com.hsebank.Main`.


## 3) Импорт и запуск в IntelliJ IDEA


1. File -> Open... -> выберите корневую папку проекта (там где `build.gradle`).
2. IDEA предложит импортировать проект как Gradle — выберите **Use Gradle wrapper**.
3. Укажите JDK (рекомендуется Java 17) для проекта: File -> Project Structure -> Project SDK.
4. Откройте Gradle tool window (справа) — найдите Tasks -> application -> `run`. Дважды кликните `run`, чтобы запустить через wrapper.


Также можно создать конфигурацию Run/Debug:
- Run -> Edit Configurations -> + -> Application
- Name: HSE-Bank
- Main class: `com.hsebank.Main`
- Use classpath of module: выберите модуль (IDEA обычно создаёт автоматически)
- Для запуска через Gradle wrapper используйте Gradle конфигурацию: + -> Gradle -> Tasks: `run`.


## 4) Советы
- Если вы хотите, чтобы wrapper уже присутствовал в репозитории — сгенерируйте wrapper локально и закоммитьте файлы `gradlew`, `gradlew.bat` и `gradle/wrapper/*`.
- При CI используйте `./gradlew build` для сборки и тестов.
