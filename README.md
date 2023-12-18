# share-it 
Cервис для шеринга вещей учебный проект Яндекс.Практикум.   
Он позволяет рассказывать какими вещами пользователи готовы поделиться или найти нужную им вещь и взять её в аренду на какое-то время. 

В проекте использовал: 
- Spring Boot
- RESTful CRUD API 
- Maven 
- JPA and Hibernate
- PostgreSQL
- Docker
- Lombok
- JUnit
- Mockito
- Postman

Приложение содержит два микросервиса:
- shareIt-server - содержит всю основную логику приложения за исключением валидации данных в контроллерах;
- shareIt-gateway - контроллеры, с которыми непосредственно работают пользователи вместе с валидацией входных данных.

Возможности сервиса:  
- бронировать вещь на определённые даты;
- показывать владельцу даты последнего и ближайшего следующего бронирования для каждой вещи;
- закрывать доступ к вещи на время бронирования от других желающих;
- если нужной вещи на сервисе нет, у пользователей есть возможность оставлять запросы;
- добавление отзывов, пользователи могут оставлять отзывы на вещь после того, как взяли её в аренду;
- пользователи могут отвечать на запросы друг друга.
