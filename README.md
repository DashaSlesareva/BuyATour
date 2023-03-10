# Дипломный проект по профессии «Тестировщик»
Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.
Приложение - это веб-сервис, который предлагает купить тур с оплатой по дебетовой картой или в кредит.

## Документы
* [План автоматизации](https://github.com/DashaSlesareva/BuyATour/blob/main/documents/plan.md)
* [Отчет по итогам тестирования](https://github.com/DashaSlesareva/BuyATour/blob/main/documents/report.md)
* [Отчет по итогам автоматизации](https://github.com/DashaSlesareva/BuyATour/blob/main/documents/summary.md)

## Начало работы

Для получения колпии этого проекта для запуска на локальном ПК и начала работы:
* склонируйте репозиторий себе на ПК командой 
`git clone git@github.com:DashaSlesareva/BuyATour.git`
* откройте проект в IDEA

### Prerequisites

Для использования проекта на ПК нужно установть:
* Git
* браузер Google Chrome
* IntelliJ IDEA CE
* Desktop Docker

### Установка и запуск

* Запустите приложение Desktop Docker
* Для запуска контейнеров с MySql, PostgreSQL и Node.js используйте команду `docker-compose up`
* Запуск приложения под MySQL
    * для запуска приложения используйте команду 
    ```
    java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar aqa-shop.jar
    ```
    * для запуска тестов используйте команду 
    ```
    ./gradlew -Ddb.url=jdbc:mysql://localhost:3306/app clean test
    ```
 * Запуск приложения под PostgreSQL
    * для запуска приложения используйте команду 
    ```
    java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar aqa-shop.jar
    ```
    * для запуска тестов используйте команду 
    ```
    ./gradlew -Ddb.url=jdbc:postgresql://localhost:5432/app clean test
    ```
 * Для получения отчета в браузере Allure используйте команду `./gradlew allureServe`
 * После окончания тестов остановите контейнеры командой `docker-compose down` и завершите работу приложения
