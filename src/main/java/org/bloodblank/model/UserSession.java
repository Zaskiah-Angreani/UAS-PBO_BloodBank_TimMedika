package org.bloodblank.model;

public class UserSession {
    public static String registeredUsername = "";
    public static String registeredPassword = "";

    // Data tambahan untuk profil
    public static String fullName = "User Baru";
    public static String bloodType = "-";

    // Method untuk membersihkan sesi saat Logout
    public static void clearSession() {
        registeredUsername = "";
        registeredPassword = "";
        fullName = "User Baru";
        bloodType = "-";
    }
}