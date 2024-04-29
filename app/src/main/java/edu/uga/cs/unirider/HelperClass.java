package edu.uga.cs.unirider;

public class HelperClass {

    String fullName, email, username, password;
    Integer userPoints;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserPoints() { return userPoints; }

    public void setUserPoints(Integer userPoints) { this.userPoints = userPoints; }

    public HelperClass(String fullName, String email, String username, String password, Integer userPoints) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userPoints = userPoints;
    }

    public HelperClass() {
    }
}