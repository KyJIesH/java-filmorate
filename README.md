# java-filmorate
В данном проекте реализовано приложение Filmorate на Spring Boot.

В приложении есть возможность создавать, редактировать пользователей, а также получать список всех пользователей. Также есть возможность создавать, редактировать фильмы, а также получать список всех фильмов. Пользователь может поставить лайк фильму, а также удалить лайк у фильма. Можно добавить пользователя в друзья (данная операция автоматически взаимная), удалить из друзей, а также получить список общих друзей двух пользователей и топ фильмов по количеству лайков.

Взаимодействие с приложением происходит по API.
Методы для работы с пользователями:
POST /users - создание пользователя
PUT /users - редактирование пользователя
GET /users - получение списка всех пользователей
GET /users/{userId} - получение информации о пользователе по его id
PUT /users/{userId}/friends/{friendId} - добавление пользователя в друзья другому пользователю
DELETE /users/{userId}/friends/{friendId} - удаление пользователя из друзей другого пользователя
GET /users/{userId}/friends - получение списка друзей пользователя
GET /users/{userId}/friends/common/{otherUserId} - получение списка общих друзей двух пользователей

Методы для работы с фильмами:
POST /films - создание фильма
PUT /films - редактирование фильма
GET /films - получение списка всех фильмов
GET /films/{filmId} - получение информации о фильме по его id
PUT /films/{filmId}/like/{userId} - проставление лайку фильму пользователем
DELETE /films/{filmId}/like/{userId} - удаление лайка у фильма пользователем
GET /films/popular - получение топа самых популярных фильмов по количеству лайков (если у двух фильмов одинаковое количество лайков, то они сортируются по имени)

Для создания и редактирования пользователя добавлены валидационные правила:

электронная почта не может быть пустой и должна содержать символ @
логин не может быть пустым и содержать пробелы
имя для отображения может быть пустым (в таком случае будет использован логин)
дата рождения не может быть в будущем
Для создания и редактирования фильма добавлены валидационные правила:

название не может быть пустым
максимальная длина описание - 200 символов
дата релиза - не раньше 29 декабря 1895 года
продолжительность фильма должна быть положительной
рейтинг фильма не может быть пустым
В приложении добавлено логирование запросов, а также логирование исключений при некорректных входящих данных.

## Дополненительная функциональность
При командной доработке данного проекта реализовал 2 функцилнальности:
1. функцилнальность поиска "Add search". Ссылка на мой pull request при командной разработке:
   
https://github.com/awdiru/java-filmorate/pull/16

2. функцилнальность добавления режиссеров "Add director". Ссылка на мой pull request при командной разработке:

https://github.com/awdiru/java-filmorate/pull/13


### Схема базы данных
![](https://github.com/KyJIesH/java-filmorate/blob/main/src/main/resources/schema.png?raw=true)

### Примеры запросов
#### FILMS
* `Получение` фильма `по идентификатору`:
```SQL
SELECT f.film_id,
       f.name,
       f.description,
       f.release_date,
       f.duration_minutes,
       mp.name AS mpa_rating,
       g.name AS genre
FROM films AS f
JOIN mpa_ratings AS mp ON f.mpa_rating_id = mp.rating_id
JOIN film_genres AS fg ON f.film_id = fg.film_id
JOIN genres AS g ON fg.genre_id = g.genre_id  
WHERE f.film_id = ?;
```   

* `Получение всех` фильмов:

```SQL
SELECT f.film_id,
       f.name,
       f.description,
       f.release_date,
       f.duration_minutes,
       mp.name AS mpa_rating,
       GROUP_CONCAT(g.name) AS genres
FROM films AS f
JOIN mpa_ratings AS mp ON f.mpa_rating_id = mp.rating_id
JOIN film_genres AS fg ON f.film_id = fg.film_id
JOIN genres AS g ON fg.genre_id = g.genre_id
GROUP BY f.film_id;
```

* `Получение топ-N (наиболее популярных)` фильмов:
```SQL
SELECT f.film_id,
       f.name,
       f.description,
       f.release_date,
       f.duration_minutes,
       mp.name AS mpa_rating,
       g.name AS genre,
       COUNT(l.user_id) AS like_count
FROM films f
JOIN mpa_ratings AS mp ON f.mpa_rating_id = mp.rating_id
JOIN film_genres AS fg ON f.film_id = fg.film_id
JOIN genres AS g ON fg.genre_id = g.genre_id
LEFT JOIN likes AS l ON f.film_id = l.film_id
GROUP BY f.film_id,
         mp.name,
         g.name
ORDER BY like_count DESC
LIMIT ?;
```

#### USERS
* `Получение` пользователя `по идентификатору`:

```SQL
SELECT *
FROM users
WHERE user_id = ?
```   

* `Получение всех` пользователей:

```SQL
SELECT *
FROM users
``` 

* `Получение` списка `общих друзей` с другим пользователем:

```SQL
SELECT u.user_id,
       u.name  
FROM users AS u
LEFT JOIN friends AS f ON u.user_id = f.second_user_id

SELECT second_user_id
FROM friends 
WHERE first_user_id = ? 
      AND second_user_id = ANY(SELECT second_user_id 
                               FROM friends 
                               WHERE first_user_id = ?)
``` 

#### GENRES
* `Получение` жанра `по идентификатору`:

```SQL
SELECT *
FROM genres
WHERE genre_id = ?
``` 

* `Получение всех` жанров:

```SQL
SELECT *
FROM genres
```   
#### RATINGS_MPA
* `Получение` рейтинга MPA `по идентификатору`:

```SQL
SELECT *
FROM mpa_ratings
WHERE rating_id = ?
``` 

* `Получение всех` рейтингов MPA:

```SQL
SELECT *
FROM mpa_ratings
```   
