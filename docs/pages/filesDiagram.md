## Диаграмма файлов приложения
[Home](../index.md)    
[Функциональные требования](functionalRequirements.md)  
[Диаграмма файлов приложения](filesDiagram.md)  
[Дополнительная спецификация](additionalSpecification.md)   
[Схема базы данных](databaseSchema.md)  
[Презентация проекта](projectPresentation.md)

## Диаграмма файлов приложения
Основные пакеты проекта &mdash; `java`, `res` и `manifests`.  
* Пакет `java` содержит исходные коды классов java, реализующих логику, представления и их контроллеры, а также реализацию ui- и unit-тестов.   
* Пакет `res` содержит различные ресурсы приложения. Здесь находятся xml-файлы разметки экранов, drawable-ресурсы, строки, цвета и другие ресурсы приложения. 
* Пакет `manifests` содержит файл манифеста приложения.

Классы приложения разделены на два пакета &mdash; ``Model``, который содержит логику приложения, и ``View``, который содержит реализацию представлений.
Реализация логики включает создание локальной базы данных приложения (`DBOpenHelper`), запросы к базе данных (`DBConnector`), сетевые запросы через TMDB API и обработка их результатов для получения информации о фильмах (`TMDBConnector`).
Пакет ``View`` содержит классы и интерфейсы, необходимые для наполнение содержимого экранов, обработки взаимодействий пользователя с элементами интерфейса.
Есть классы, соответствующие основным экранам приложения(`Login`,`CinemaList`,`CinemaSessions`,`MyTickets`,`SessionSeats`,`BuyTicket`,`MovieActivity`).
При реализации интерфейса использованы элементы `RecyclerView`, поэтому необходимые подклассы ``RecyclerView.Adapter<>`` и интерфейсы для работы с ними также расположены в пакете ``View``.

Реализация ui-тестов &mdash; в классе ``LoginTest``, а unit-тестов &mdash; в классе ``ExampleUnitTest``.

### Диаграмма файлов приложения
![диаграмма файлов общая 2](https://user-images.githubusercontent.com/78850433/212052762-e49e4696-7be4-490e-9fd7-83a3385f471e.png)
### Диаграмма файлов для пакета Model
![диаграмма файлов для Model ](https://user-images.githubusercontent.com/78850433/212042471-f08aa020-f39d-44b2-81db-b8381b697879.png)
### Диаграмма файлов для пакета View
![диаграмма файлов для View](https://user-images.githubusercontent.com/78850433/212046682-51b59237-907b-4220-a04a-2c55b7b95f96.png)

