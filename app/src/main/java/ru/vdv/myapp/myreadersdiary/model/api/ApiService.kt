package ru.vdv.myapp.myreadersdiary.model.api

/**
## [Dad Approves API Docs](https://dadapproves.ru/docs/overview-resources-in-the-rest-api.php)
### Сводный (обобщающий) интерфейс API Service
подключенные интерфейсы компонент:
- [DApiUsers] - интерфейс компоненты [API USERS (книги)](https://dadapproves.ru/docs/overview-resources-in-the-rest-api.php)
- [DApiBooks]  - интерфейс компоненты [API BOOKS (Книги)](https://dadapproves.ru/docs/overview-resources-in-the-rest-api.php)
- [DApiEvents] - интерфейс компоненты [API EVENTS (События)](https://dadapproves.ru/docs/overview-resources-in-the-rest-api.php)
 */
interface ApiService : DApiBooks, DApiEvents, DApiUsers {
    // в теле интерфейса ммогут быть размещены методы общего характера, тестовые и не относящиеся
    // явно к конкретным компонентам сервиса. Если метод стандартный для компонента, имеет
    // ссылку н подробное описание и оформлен в полном соответствии с документацией
    // желательно его размещать в соответствующем файле интерфейса
}