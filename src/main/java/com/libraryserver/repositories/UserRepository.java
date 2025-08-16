package com.libraryserver.repositories;

import com.libraryserver.model.User;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap.KeySetView<String, Boolean> usernames = ConcurrentHashMap.newKeySet();

    public User save(User user) {
        if (!usernames.add(user.getName().toLowerCase())) {
            throw new IllegalArgumentException("Username already exists");
        }
        users.put(user.getId(), user);
        return user;
    }

    public User findById(String id) {
        return users.get(id);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
