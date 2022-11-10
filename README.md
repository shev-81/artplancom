<h1 align="center">ArtPlan & S3 MiniO
<h3 align="center">Тестовое задание (Java Spring Boot, PostgreSQL, S3 (MiniO), MS Architecture)</h3>


## 0. Предисловие 

В рамках исполнения технического задания от компании, необходимо реализовать набор сервисов для WEB приложения. 
В качестве базы данных PostgreSQL. Spring Boot 2 и Hibernate (JPA). 

<b>`Реализован дополнительный функционал загрузки изображений в S3 хранилище MiniO.`</b>

В контексте данного задания не рассматривается функционал загрузки изображения на сервер, а используются изображения уже находящиеся на стороне бэкенда.
Хотя реализовать прямую загрузку из потока ввода вывода вполне возможно.

> <b>Техническая часть</b>
 - `IDE: IntelliJ IDEA 2021.3.3`
 - `Версия JDK: 17.0.1 .`
 - `Postgresql`
 - `Docker`, `FlyWay`
 - `Spring Boot 2`
 - `S3 MiniO`
> <b>Используемые технологии:</b>
 - `Spring Boot`, `Hibernate`, `Data Jpa` 
 - `Angular JS`, `HTML`, `Bootstrap`, `CSS`
 - `Spring Cloud`, `Feign client`
 - `Lombok`
 - `Stream API`
 - `Мавен 3.5`

![main](https://user-images.githubusercontent.com/89448563/201149874-2878c981-02ec-4214-a9f9-ce0f488fffcb.png)

Несколько сервисов: GateWay, Auth-Service, Core-Service и FrontService.


## 1. MS GateWay 
- принимает все входящие запросы и перенаправляет по MS, а так же проверяет валиден ли токен JWT.
#### MS Auth-Service 
- работает с пользователями системы, его задача при успешной авторизации выдать токен JWT пользователю. Здесь нужно сказать, что форму регистрации новых пользователей я сделал в разделе, который требует от пользователя быть регистрированным. В связи с этим взял на себя и завел несколько пользователей сразу в систему. Пользователи хранятся в БД Postgress, как и просили работаем с ними используя ОRМ Hibernate.
 
    #### логин       пароль
    #### admin        100
    #### user         100
    #### manager      100

## 2. MS Core-Service 
- работает с контентом Животных,  Animals хранятся в БД Postgress (это вторая БД она не относится к БД с Users), как и ранее работаем с ними используя ОRМ Hibernate. Не стал усложнять и делать углубленную структуру таблиц и делать связи ORM (1 : M).
Поэтому они все в 1 табличке : ). В контроллере настроены все необходимые Энд поинты согласно REST. Как и просили работаем с JSON форматом.

## 3. MS Front-Service 
- Содержит весь фронт приложения в нем 3 раздела: Основной на нем выложено ваше ТЗ в исходнике. Раздел Список справочник в котором показывается список животных пользователя прошедшего регистрацию (Для разных пользователей список свой). и форма для добавления животного. Если ввести имя животного, которое уже есть в системе вернется ошибка. Раздел Пользователей список пользователей в системе с их правами доступа (тут нужно сказать, что настройку доступов в зависимости от прав я тут, не настраивал, как и сказал ранее есть над чем еще работать).


## 4. Интеграция MiniO!

> MinIO — это высокопроизводительное решение для хранения объектов, которое предоставляет API, совместимый с Amazon Web Services S3, и поддерживает все основные функции S3.

Быстрый старт для Docker контейнера . Hассматривается развертывание MiniO в 1 контейнере Docker, хотя система так же адаптирована к использованию с  Kubernetes и Openshift, а так же имеет возможности для гибкой настройки развертывания с использованием балансировщиков нагрузки таких как NGINX.

 - Необходим установленный Docker
 - Доступ для чтения, записи и удаления к папке или диску, используемому для постоянного тома.
 
Что бы развернуть S3 MiniO, необходимо выполнить следующий скрипт `docker-conpose.yaml` файл командой докера `docker-conpose up`:

    version: '3.7'
    services:
      minio-service:
        image: quay.io/minio/minio
        volumes:
          - c:\minio\data:/data
        ports:
          - "9000:9000"
          - "9090:9090"
        environment:
          MINIO_ROOT_USER: minioroot
          MINIO_ROOT_PASSWORD: minioroot
        command: server --address ":9000" --console-address ":9090" /data
        
Результат, это развернутый контейнер с MiniO. 
 
 > Консоль Управления. Консоль управления доступна по адресу http://127.0.0.1:9090/login. 

![consoleMiniO](https://user-images.githubusercontent.com/89448563/201153872-b6b82b02-fb8d-47f7-a646-e919eb8fe20e.png)

Консоль управления доступна для учетной записи root определенной в конфигурации образа. Для данного конкретного случая:

    login:           minioroot

    Password:        minioroot

При успешном прохождении авторизации, откроется панель управления MiniO.

![console2](https://user-images.githubusercontent.com/89448563/201154293-9a103b95-855f-4b3e-848d-9b9b08297603.png)

Детально о функционале доступном в панели управления <a href = https://min.io/docs/minio/container/administration/minio-console.html>можно  тут.</a>

> <b>Java Spring интеграция в проект.  </b>

Для успешной интеграции и использования потребуется подтянуть зависимости Maven, это библиотека MiniO и клиент okhttp.

        <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
            <version>8.4.5</version>
        </dependency>
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>4.9.3</version>
        </dependency>

Теперь необходимо определить конфигурационный файл для использования MiniO клиента. Добавляем настройки в конфигурационный файл.

        miniO:
          server:
            address: http://127.0.0.1:9000
          user:
            login: minioroot
            password: minioroot
          container: bucket
          
И можем приступить к описанию конфигурации как компонента Spring. Создаем класс конфигурации MinioConfig как на примере ниже.

        @Configuration
        public class MinioConfig {

            @Value("${miniO.server.address}")
            String address;

            @Value("${miniO.user.login}")
            String login;

            @Value("${miniO.user.password}")
            String password;


            @Bean
            public OkHttpClient OkHttpClientFactory() {
                return new OkHttpClient();
            }

            @Bean
            public MinioClient getMiniOClient(){
                try {
                    MinioClient minioClient =
                            MinioClient.builder()
                                    .endpoint(address)
                                    .credentials(login, password)
                                    .httpClient(OkHttpClientFactory())
                                    .build();
                    return minioClient;
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        
> Адрес сервера  http://127.0.0.1:9000  login: minioroot  password: minioroot   
Логин и пароль root и используются, как для работы клиента в коде,  так и для входа в консоль.

## 5. Практическое использование.  Положить объект в корзину MiniO.

У клиента есть множество методов <a href = https://min.io/docs/minio/linux/developers/java/API.html>подробно тут.</a>

Как положить объект в хранилище и как получить ссылку на него в Bucket хранилища.
Для помещения объекта в хранилище используем следующий код  `putObject(Object obj)`.

        @Autowired
        private final MinioClient minioClient;

        public boolean putObject(Object obj) {
            try {
                minioClient.uploadObject(
                        UploadObjectArgs.builder()
                                .bucket("bucket")
                                .object("name obj")
                                .filename("path to file on server" )
                                .build());
                System.out.println( "File is successfully uploaded.");
            } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                System.out.println("Error occurred: " + e);
                return false;
            }
            return true;
        }
        
В данном примере предполагается, что файл помещаемый в хранилище MiniO находится на стороне сервера. Корзина уже должна быть создана в MiniO !!! в данном примере это - `bucket`.

> Корзину можно не создавать вручную а создать ее на лету при выполнении программы или создать ее при запуске программы. 

Пример как создать корзину если ее пока еще не существует.

        @Autowired
        private final MinioClient minioClient;

        @PostConstruct
        private void init(){
            try {
                boolean found = minioClient.bucketExists(BucketExistsArgs
                        .builder()
                        .bucket("bucket")
                        .build());
                if (!found) {
                    // Make a new bucket called 'bucket'.
                    minioClient.makeBucket(MakeBucketArgs
                            .builder()
                            .bucket("bucket")
                            .build());
                } else {
                    System.out.println("Bucket " + bucket + " already exists.");
                }
            }catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e){
                System.out.println("Error : " + e);
            }
        }

## 6. Практическое использование.  Получить ссылку на объект в корзине MiniO. 

Для получения объекта корзины используем следующий код.

            public String getUrlObject(Object obj) {
                String url = null;
                try {
                    url = minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket("bucket"")
                                    .object("obj name")
                                    .expiry(1, TimeUnit.DAYS)
                                    .build());
                } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                System.out.println(url);
                return url;
            }
            
Если все в порядке то  в  url  вернется подписанная ссылка, которая в купе с адресом объекта в корзине вернет мета информацию в данном примере содержащую срок действия этой ссылки 1 день  .expiry(1, TimeUnit.DAYS). По этой ссылке объект уже можно показать в браузере. 
Как видно из примера метод .object("obj name") принимает имя объекта.  Именно по этому имени объект идентифицируется в “bucket“.

## 7. Как запустить?! 

Для запуска приложения необходим установленный Docker на системе! В корне проекта есть каталог Flyway и в нем файл запуска  start-migration.bat просто запускаем его. Будет развернуто 2 БД Postgress в контейнерах докера, и запущены скрипты миграции для создания структур БД  и их первичного наполнения. 

Все мы готовы к первому запуску! запускаем все 4  MS’а в IDE, открываем браузер по адресу http://localhost:3000/front   смотрим… должна загрузится стартовая страничка с ТЗ.

Клиентская часть (Front-Service) написан с использованием Angular JS, Boottstrap стека технологий.
В BackEnd использован Spring Boot. 
