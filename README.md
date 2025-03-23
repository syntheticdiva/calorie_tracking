API для системы учёта калорий. Позволяет отслеживать приёмы пищи, управлять блюдами и генерировать отчёты.

## Функционал

- **Пользователи**
    - Регистрация пользователя с расчётом дневной нормы калорий
    - Просмотр списка пользователей
    - Поиск пользователя по ID

- **Блюда**
    - Добавление новых блюд с питательной информацией
    - Просмотр каталога блюд

- **Приёмы пищи**
    - Фиксация приёмов пищи с указанием блюд и порций
    - Просмотр дневной истории питания
    - Получение полной истории приёмов пищи

- **Отчёты**
    - Генерация дневного отчёта с суммарными показателями:
        - Общее количество калорий
        - Количество приёмов пищи
        - Соответствие дневной норме

##  Технологии

- Java 17
- Spring Boot 3
- PostgreSQL
- Hibernate
- Lombok
- MapStruct
- Swagger (OpenAPI 3)

## Запуск проекта

### Требования
- Установленные:
    - Java 17+
    - Maven 3.9+
    - PostgreSQL 14+

### Настройка

1. Создайте БД в PostgreSQL:
   ```sql
   CREATE DATABASE calorie_tracking;
Настройте подключение в application.properties (редактировать можно будет с помощью notepad++):

text
spring.datasource.url=jdbc:postgresql://localhost:5432/calorie_tracking


spring.datasource.username=ваш_логин


spring.datasource.password=ваш_пароль


Соберите и запустите приложение:

bash
mvn clean install
java -jar target/calorie-tracking-1.0.0.jar


 API Документация
После запуска приложения документация доступна по ссылке:
http://localhost:8080/swagger-ui/index.html

Основные эндпоинты:
\\
POST	/users	                Создание пользователя
\\
GET	    /users	                Все пользователи
\\
GET	    /users/{id}	            Пользователь по ID
\\
POST	/meals	                Добавить блюдо
\\
POST	/meal-entries	        Запись приёма пищи
\\
GET 	/meal-entries/report	Дневной отчёт
\\


 Примеры запросов
Создание пользователя
text
POST /users
Content-Type: application/json

{
"name": "Иван Иванов",
"email": "user@example.com",
"age": 30,
"gender": "MALE",
"weight": 75.5,
"height": 180.0,
"goal": "WEIGHT_LOSS"
}


Получение дневного отчёта
text
GET /meal-entries/report?userId=1&date=20.03.2024


       Postman-коллекция
Скачать коллекцию
Импортируйте файл в Postman для тестирования API.

Коллекция включает:

  - Все CRUD-операции

  - Примеры тел запросов

  - Автоматические проверки статусов ответов

  - Переменные окружения для быстрого тестирования

text

### Postman-коллекция ([Скачать пример](CalorieTracking.postman_collection.json))

```json
{
  "info": {
    "name": "Calorie Tracking API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Users",
      "item": [
        {
          "name": "Create User",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Test User\",\n  \"email\": \"test@example.com\",\n  \"age\": 25,\n  \"gender\": \"FEMALE\",\n  \"weight\": 60,\n  \"height\": 165,\n  \"goal\": \"MAINTENANCE\"\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "{{baseUrl}}/users",
              "host": ["{{baseUrl}}"],
              "path": ["users"]
            }
          }
        }
      ]
    },
    {
      "name": "Meals",
      "item": [
        {
          "name": "Add Meal",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Овсяная каша\",\n  \"caloriesPerServing\": 150,\n  \"proteins\": 5,\n  \"fats\": 3,\n  \"carbohydrates\": 25\n}",
              "options": {
                "raw": {
                  "language": "json"
                }
              }
            },
            "url": {
              "raw": "{{baseUrl}}/meals",
              "host": ["{{baseUrl}}"],
              "path": ["meals"]
            }
          }
        }
      ]
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080"
    }
  ]
}


Для использования:

Сохраните JSON-файл

Импортируйте в Postman через File → Import

Настройте переменные окружения при необходимости

Тестируйте эндпоинты с примерами данных