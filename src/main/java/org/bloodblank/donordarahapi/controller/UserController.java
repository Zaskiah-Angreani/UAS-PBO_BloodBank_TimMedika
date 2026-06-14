package org.bloodblank.donordarahapi.controller;

import jakarta.validation.Valid;
import org.bloodblank.donordarahapi.entity.User;
import org.bloodblank.donordarahapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/dummy")
    public User createDummyUser() {

        User user = new User();

        user.setUsername("admin");
        user.setPassword("admin123");
        user.setRole("ADMIN");
        user.setNama("Administrator");

        return userService.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){

        return userService.login(
                user.getUsername(),
                user.getPassword()
        );
    }
}