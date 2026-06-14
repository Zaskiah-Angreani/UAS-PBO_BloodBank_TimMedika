package org.bloodblank.donordarahapi.service;

import org.bloodblank.donordarahapi.entity.User;
import org.bloodblank.donordarahapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User login(String username, String password){

        return userRepository
                .findByUsernameAndPassword(username, password)
                .orElse(null);
    }
}