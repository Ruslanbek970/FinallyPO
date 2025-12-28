# hotelbooking

## Описание
Проект — backend-приложение для бронирования отелей. По коду реализованы сущности и REST API для:
- пользователей и ролей (`User`, `Role`), регистрации и логина;
- городов (`City`), отелей (`Hotel`), комнат (`Room`), удобств (`Amenity`);
- бронирований (`Booking`), гостей бронирования (`Guest`), оплат (`Payment`), отзывов (`Review`);
- админских операций над пользователями (`/api/admin/*`) и операций менеджера (`/api/manager/*`).

## Технологии
- Java: **17** (см. `build.gradle`: `java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }`).
- Spring Boot: плагин `org.springframework.boot` версии **4.0.1** (см. `build.gradle`).
- Сборка: **Gradle** (есть Gradle Wrapper: `gradlew`, `gradlew.bat`).
- База данных: **PostgreSQL** (драйвер `org.postgresql:postgresql`).
- Миграции: **Liquibase** (`org.liquibase:liquibase-core`).
- Security: **Spring Security** (используется сессионная аутентификация, см. `AuthController` и `SecurityConfig`).
- MapStruct: **MapStruct** (`org.mapstruct:mapstruct` + `mapstruct-processor`).
- Lombok: **Lombok**.
- Тесты: JUnit 5 + Mockito (`org.mockito:mockito-core`).

> В `build.gradle` также подключён `spring-boot-starter-thymeleaf`, но в проекте не найдено `src/main/resources/templates`.

## Требования для запуска
- JDK **17**
- Docker (опционально, но рекомендовано): для запуска PostgreSQL через `docker-compose.yml`

## Быстрый старт
1) Поднять БД (PostgreSQL) через Docker Compose:
```bash
cd FinallyPO
# База данных для приложения (порт 2345 на хосте)
docker compose up -d main-db
```

2) Запустить приложение:
```bash
cd FinallyPO
./gradlew bootRun
# Windows: gradlew.bat bootRun
```

3) Приложение будет доступно на `http://localhost:8080` (см. `server.port` в `application.properties`).

## Как запустить локально
### Вариант 1: с Docker Compose (рекомендуется)
Поднимите PostgreSQL (как в `docker-compose.yml`) и запустите Spring Boot.
```bash
cd FinallyPO
# 1) поднять БД
docker compose up -d main-db

# 2) запуск приложения
./gradlew bootRun
```

### Вариант 2: своя PostgreSQL
Если вы запускаете PostgreSQL вручную, настройте параметры подключения (см. раздел «Конфигурация»), чтобы БД была доступна по URL из `application.properties`.

## Как запустить через Docker Compose
В проекте есть `docker-compose.yml` с двумя сервисами:
- `main-db` — БД для приложения, проброшена на порт **2345**;
- `test-db` — БД для тестов, проброшена на порт **2346**.

Команды:
```bash
cd FinallyPO
# поднять только основную БД
docker compose up -d main-db

# поднять обе БД
# (например, если хотите запускать тесты с профилем test)
docker compose up -d main-db test-db

# остановить
# docker compose down
```

## Конфигурация
### `src/main/resources/application.properties`
```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:2345/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
spring.liquibase.enabled=true
```

### `src/test/resources/application-test.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:2346/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

spring.liquibase.change-log=classpath:db/changelog/db.changelog-test.xml
spring.liquibase.enabled=true
```

#### Переменные окружения
В проекте не найдено использование переменных окружения для конфигурации (например, `${...}` в `application.properties/yml`).

## База данных и миграции
- СУБД: PostgreSQL.
- Миграции выполняет Liquibase при старте приложения (`spring.liquibase.enabled=true`).
- Главный changelog: `classpath:db/changelog/db.changelog-master.xml`.
- Файлы миграций лежат в `src/main/resources/db/changelog/changes/`.

### Таблицы (по Liquibase)
Создаются таблицы:
- `roles`
- `users`
- `users_roles`
- `cities`
- `hotels`
- `amenities`
- `rooms`
- `rooms_amenities`
- `bookings`
- `guests`
- `payments`
- `reviews`

### Seed-данные
Файл `src/main/resources/db/changelog/changes/002-seed-data.xml` добавляет роли:
- `ROLE_ADMIN`
- `ROLE_MANAGER`
- `ROLE_USER`
и пользователей:
- `ruslanbek@booking.kz` (`full_name`: `System Admin`)
- `kasym@booking.kz` (`full_name`: `Hotel Manager`)
- `maksat@booking.kz` (`full_name`: `Regular User`)

> В seed-данных хранятся **bcrypt-хэши** (`password_hash`). Исходные пароли в проекте не найдены.

## Аутентификация и роли
### Как логиниться
Endpoint: **POST** `/api/auth/login`

Request (`LoginRequestDto`):
```json
{
  "email": "nouneym@test.com",
  "password": "nouneympassword05!"
}
```

Response (`LoginResponseDto`):
```json
{
  "id": 1,
  "email": "nouneym@test.com",
  "fullName": "Example User",
  "roles": [
    "string"
  ]
}
```

Важно: в `AuthController` после успешной аутентификации создаётся HTTP-сессия и клиенту нужно сохранить cookie **`JSESSIONID`** (см. комментарий в коде).

## Тестирование
### Запуск всех тестов
```bash
cd FinallyPO
./gradlew test
```

