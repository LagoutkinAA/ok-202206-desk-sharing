# Desk Sharing System
Учебный проект курса Kotlin Backend Developer. Desk-Sharing - это система поиска и резервации рабочих мест на ИТ-предприятии.

Система позволяет осуществлять поиск свободного рабочего места по определенным критериям и резервировать его за работником на определенный день.

## Учебный маркетинг приложения.
### Целевая аудитория системы: 
Мелкие и средние ИТ-предпрятия использующие гибридный режим работы сотрудников (совмещение удаленной работы и работы из офиса) и нуждающиеся в системе, позволяющей сотрудникам резервировать за собой рабочее место при необходимости выйти в офис.

### Гипотетический портрет заказчика:
Руководитель ИТ-предприятия
1. Мужчина в возрасте от 30 до 50 лет.
2. Состоятельный, имеет жильё и автомобиль, скорее всего женат и имеет детей.
3. С высшим техническим образованием.
4. Обладает критическим мышлением и аналитическим складом ума, открыт для инноваций.
5. Умеет считать деньги и оптимизировать расходы.
### Гипотетический портрет пользователя:
Высококвалифицированный разработчик (middle, senior)
1. Преимущественно мужчина в возрасте от 30 до 60 лет.
2. С высшим техническим образованием.
3. Обладает критическим мышлением и аналитическим складом ума. 

### Функции:
CRUDS (create (book), read, update, delete (unbook), search) для заявки на бронирование рабочего места (Demand)

### Сущности: 
1. Заявка на бронирование рабочего места Demand
    - номер заявки
    - дата заявки
    - сотрудник    - 
    - дата бронирования
    - статус (новая, исполнена, отказана, отменена, подтверждена)
    - номер зарезервированного рабочего места (доступные номера рабочих мест на предприятии задаются в настройках приложения: префикс, минимальное значение, максимальное значение)
    - причина отказа

# Структура проекта

## Транспортные модели, API

1. [specification](specification) - описание API в форме OpenAPI-спецификаций
2. [desk-sharing-transport-main-openapi](desk-sharing-transport-main-openapi) - Генерация транспортных моделей с
   Jackson
3. [desk-sharing-common](desk-sharing-common) - модуль с общими классами для модулей проекта (внутренние модели, контекс).
4. [desk-sharing-mappers](desk-sharing-mappers) - Мапер между внутренними моделями и моделями API

## Фреймворки и транспорты