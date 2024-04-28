package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Like {
    @NotNull
    private Long filmId;
    @NotNull
    private Long userId;
}
