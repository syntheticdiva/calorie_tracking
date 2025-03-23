API для системы учёта калорий. Позволяет отслеживать приёмы пищи, управлять блюдами и генерировать отчёты.

## Функционал

- **Пользователи**
    - Регистрация пользователя с расчётом дневной нормы калорий
    - Просмотр списка пользователей
    - Поиск пользователя по ID

- **Блюда**
    - Добавление блюд с параметрами
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
   
Настройте подключение переменныхп окружения в application.properties (редактировать можно будет с помощью notepad++):

- **spring.datasource.url=jdbc:postgresql://localhost:5433/calorie_tracking**
- **spring.datasource.username=ваш_логин**
- **spring.datasource.password=ваш_пароль**

## Соберите и запустите приложение:

- **В корневой папке вызовите cmd и введите команду**

## mvn clean install

затем

## java -jar target/calorie_tracking-0.0.1-SNAPSHOT.jar


## После запуска приложения документация доступна по ссылке:
## http://localhost:8080/swagger-ui/index.html

## Основные эндпоинты:

- **POST	/users	            Создание пользователя**

- **GET	    /users	                Все пользователи**
- **GET	    /users/{id}	            Пользователь по ID**
- **POST	/meals	                Добавить блюдо**
- **POST	/meal-entries	        Запись приёма пищи**
- **GET 	/meal-entries/report	Отчет**

##  Примеры запросов для Postman (либо можно протестить в Swagger)

- **Создание пользователя**
text
POST http://localhost:8080/users
Content-Type: application/json

{
"name": "Иван Иванов",
"email": "user3@example.com",
"age": 30,
"gender": "MALE",
"weight": 70.5,
"height": 175,
"goal": "WEIGHT_LOSS"
}

- **Получение всех пользователей: GET http://localhost:8080/users**

- **Получение пользователя по id: GET http://localhost:8080/users/1**

- **Получение списка блюд: GET http://localhost:8080/meals**

- **Создание нового блюда:
text
POST Content-Type: application/json  
http://localhost:8080/meals
{
"id": 1,
"name": "Овсяная каша",
"caloriesPerServing": 150,
"proteins": 5.5,
"fats": 3.2,
"carbohydrates": 20
}**

- **Получение дневного отчёта
text
GET /meal-entries/report?userId=1&date=20.03.2024**

- **Получение блюда по id: GET http://localhost:8080/meals/1**

- **Внесение блюд для пользователя:
text
POST Content-Type: application/json  
http://localhost:8080/meal-entries
{
"userId": 1,
"meals": [
{
"mealId": 1,
"quantity": 3
}
],
"date": "20.03.2025"
}**

- **Получение отчет за день с суммой всех калорий и приемов пищи:
GET http://localhost:8080/meal-entries/report?userId=1&date=21.03.2025**


- **Получние истории питания за всё время: 
GET http://localhost:8080/meal-entries/history?userId=1**

- **Получение истории питания по дням:
GET http://localhost:8080/meal-entries/daily?userId=1&date=21.03.2025**

