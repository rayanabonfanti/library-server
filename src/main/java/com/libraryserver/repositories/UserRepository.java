package com.libraryserver.repositories;

import com.libraryserver.model.User;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public User save(User user) {
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
