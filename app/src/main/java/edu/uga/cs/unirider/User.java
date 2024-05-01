package edu.uga.cs.unirider;

public class User {

    String username, email, password;
    Integer userPoints;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserPoints() { return userPoints; }

    public void setUserPoints(Integer userPoints) { this.userPoints = userPoints; }

    public User(String email, String username, String password, Integer userPoints) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userPoints = userPoints;
    }

    public User() {
    }
}