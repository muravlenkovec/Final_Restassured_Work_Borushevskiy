# Итоговая практическая работа по автоматизации тестирования back-end

## Описание
Проект содержит тесты на Restassured, а также коллекцию в .json формате для запуска в Postman (в директории src\resources\postman\collections)

## Как запустить тесты
1. Поднять локально учебный стенд
```bash
--Перейти в директорию Final_Restassured_Work_Borushevskiy\bin
cd C:\Final_Restassured_Work_Borushevskiy\bin

--Запустить стенд
java -jar qualit-sandbox.jar
```
2. В IDEA клонировать проект к себе на машину, перейти в консоль, выполнить команду
```bash
mvn clean test
```
Тесты будут выполнены и в консоли IDEA будут отображены результаты и логи
