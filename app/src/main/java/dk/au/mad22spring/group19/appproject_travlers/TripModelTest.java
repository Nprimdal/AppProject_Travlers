package dk.au.mad22spring.group19.appproject_travlers;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class TripModelTest {

    //Widgets:
    public String cityName, countryName, travelPlanNotes, travelJournalNotes;
    public boolean userVisitedCity;
    public double lon, lat;
    public float travelUserRating;

    //Primary key for database
    private int id;

    //Empty constructor
    public TripModelTest(){}

    //Constructor
    public TripModelTest(String cityName, String countryName, String travelPlanNotes, String travelJournalNotes, float travelUserRating, boolean userVisitedCity, double lon, double lat){
        this.cityName = cityName;
        this.countryName = countryName;
        this.travelPlanNotes = travelPlanNotes;
        this.travelJournalNotes = travelJournalNotes;
        this.travelUserRating = travelUserRating;
        this.userVisitedCity = userVisitedCity;
        this.lon = lon;
        this.lat = lat;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTravelPlanNotes() {
        return travelPlanNotes;
    }

    public void setTravelPlanNotes(String travelPlanNotes) {
        this.travelPlanNotes = travelPlanNotes;
    }

    public String getTravelJournalNotes() {
        return travelJournalNotes;
    }

    public void setTravelJournalNotes(String travelJournalNotes) {
        this.travelJournalNotes = travelJournalNotes;
    }

    public float getTravelUserRating() {
        return travelUserRating;
    }

    public void setTravelUserRating(float travelUserRating) {
        this.travelUserRating = travelUserRating;
    }

    public boolean hasUserVisitedCity() {
        return userVisitedCity;
    }

    public void setUserVisitedCity(boolean userVisitedCity) {
        this.userVisitedCity = userVisitedCity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
