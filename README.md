# java-filmorate
Написание бэкенда для сервиса, который будет работать с фильмами 
и оценками пользователей, а также возвращать топ-5 фильмов, 
рекомендованных к просмотру.

## sprint 9
1. Создание моделей данных
2. Организация хранения данных.
3. Создание REST-контроллеров.
4. Проверка данных (Валидация).
5. Логирование операций.
6. Тестирование проекта.

## sprint 11
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