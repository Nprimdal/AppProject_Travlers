package dk.au.mad22spring.group19.appproject_travlers.Models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String FullName;
    private List<TripModel> trips;

    public User(String email, String fullName) {
        this.email = email;
        FullName = fullName;
        trips = new ArrayList<>();
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
