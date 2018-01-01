# homework

Запускать проще всего прямо из IDE Application.java

Используется embedded tomcat и h2 in-memory база данных.
По умолчанию запустится на порту 8080. Порт можно изменить в файле application.properties.

Используются следующие URL
http://localhost:8080/deposit
http://localhost:8080/withdraw
http://localhost:8080/operations
http://localhost:8080/operations/{personId}

Я добавил поле created и сортирую операции по нему, просто не люблю данные в непонятном порядке.

Все настройки хранятся в ресурсах в файле application.properties. Там есть настройки для ограничителя по количеству операций из страны и настройки сервиса GeoIP (url, json поле, страна по умолчанию, timeout).

Логирую я только eхception-ы при ресолвинге страны по IP в консоль.

Я не писал никаких интеграционных тестов, только юнит-тесты.

Надо включить Lombok plugin для IntelliJ и включить annotation processing.
Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> Enable annotation processing
