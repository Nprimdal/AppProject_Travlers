package dk.au.mad22spring.group19.appproject_travlers;

public class User {
    private String email;
    private String FullName;

    public User(String email, String fullName) {
        this.email = email;
        FullName = fullName;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }


}
