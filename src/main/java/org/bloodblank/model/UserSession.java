package org.bloodblank.model;

import org.bloodblank.donordarahapi.entity.User;

public class UserSession {
    private static UserSession instance;

    private org.bloodblank.donordarahapi.entity.User currentUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentUser(org.bloodblank.donordarahapi.entity.User user) {
        this.currentUser = user;
    }

    public org.bloodblank.donordarahapi.entity.User getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        this.currentUser = null;
    }
}
