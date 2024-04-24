package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Friends {
    @NotNull
    private Long firstUserId;
    @NotNull
    private Long secondUserId;
}
