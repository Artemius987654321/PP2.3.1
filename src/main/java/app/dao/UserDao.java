package app.dao;

import app.model.User;

import java.util.List;

public interface UserDao {
    User getUserById(long id);

    void updateUser(User user);

    void removeUserById(long id);

    List<User> getAllUsers();
}
