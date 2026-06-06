package org.bloodblank.model;

public class UserSession {
    private static UserSession instance;
    private User currentUser;

    // Private constructor agar tidak bisa di-instansiasi dari luar
    private UserSession() {}

    // Singleton instance access
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Encapsulated setters/getters
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        this.currentUser = null;
    }
}