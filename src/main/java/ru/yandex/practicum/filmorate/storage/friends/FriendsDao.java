package ru.yandex.practicum.filmorate.storage.friends;

import java.util.Set;

public interface FriendsDao {

    void addFriend(Long firstUserId, Long secondUserId);

    void deleteFriend(Long firstUserId, Long secondUserId);

    Set<Long> getFriends(Long id);
}
