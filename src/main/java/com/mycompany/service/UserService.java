package com.mycompany.service;

import com.mycompany.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    Optional<User> updateUser(Long id, User userDetails);
    boolean deleteUser(Long id);
}
