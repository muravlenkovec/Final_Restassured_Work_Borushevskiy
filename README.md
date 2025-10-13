# Итоговая практическая работа по автоматизации тестирования back-end

## Описание
Проект содержит тесты на Restassured, а также коллекцию Postman

## Как запустить тесты
1. Поднять локально учебный стенд
```bash
--Перейти в директорию Final_Restassured_Work_Borushevskiy\bin
cd C:\Final_Restassured_Work_Borushevskiy\bin

--Запустить стенд
java -jar qualit-sandbox.jar
```
2. В IDEA перейти в консоль, выполнить команды
```bash
mvn clean test
mvn allure:serve
```
Тесты будут выполнены и автоматически откроется веб-браузер со страницей отчета Allure
