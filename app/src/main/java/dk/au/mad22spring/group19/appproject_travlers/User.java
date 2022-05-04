package dk.au.mad22spring.group19.appproject_travlers;

public class User {
    private String email;
    private String password;
    private String FullName;

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        FullName = fullName;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

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

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }


}
