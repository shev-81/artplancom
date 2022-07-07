# Тестовое задание artplancom


И так, что сделано: реализовано, как вы и просили несколько сервисов: GateWay, Auth-Service, Core-Service и FrontService.
Кратко о них.
#### MS GateWay 
- принимает все входящие запросы и перенаправляет по MS, а так же проверяет валиден ли токен JWT.
#### MS Auth-Service 
- работает с пользователями системы, его задача при успешной авторизации выдать токен JWT пользователю. Здесь нужно сказать, что форму регистрации новых пользователей я сделал в разделе, который требует от пользователя быть регистрированным. В связи с этим взял на себя и завел несколько пользователей сразу в систему. Пользователи хранятся в БД Postgress, как и просили работаем с ними используя ОRМ Hibernate.
 
#### логин       пароль
#### admin        100
#### user         100
#### manager      100

#### MS Core-Service 
- работает с контентом Животных,  Animals хранятся в БД Postgress (это вторая БД она не относится к БД с Users), как и ранее работаем с ними используя ОRМ Hibernate. Не стал усложнять и делать углубленную структуру таблиц и делать связи ORM (1 : M).
Поэтому они все в 1 табличке : ). В контроллере настроены все необходимые Энд поинты согласно REST. Как и просили работаем с JSON форматом.

#### MS Front-Service 
- Содержит весь фронт приложения в нем 3 раздела: Основной на нем выложено ваше ТЗ в исходнике. Раздел Список справочник в котором показывается список животных пользователя прошедшего регистрацию (Для разных пользователей список свой). и форма для добавления животного. Если ввести имя животного, которое уже есть в системе вернется ошибка. Раздел Пользователей список пользователей в системе с их правами доступа (тут нужно сказать, что настройку доступов в зависимости от прав я тут, не настраивал, как и сказал ранее есть над чем еще работать).

#### Теперь как запустить эту богодельню! 

Для запуска приложения необходим установленный Docker на системе! В корне проекта есть каталог Flyway и в нем файл запуска  start-migration.bat просто запускаем его. Будет развернуто 2 БД Postgress в контейнерах докера, и запущены скрипты миграции для создания структур БД  и их первичного наполнения. 

Все мы готовы к первому запуску! запускаем все 4  MS’а в IDE, открываем браузер по адресу http://localhost:3000/front   смотрим… должна загрузится стартовая страничка с ТЗ.

Клиентская часть (Front-Service) написан с использованием Angular JS, Boottstrap стека технологий.
В BackEnd использован Spring Boot. 
