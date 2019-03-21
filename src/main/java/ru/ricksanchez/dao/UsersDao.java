package ru.ricksanchez.dao;

import java.util.List;

import ru.ricksanchez.model.User;

public interface UsersDao extends CrudDao<User> {
    List<User> findAllByName(String firstName);
}
