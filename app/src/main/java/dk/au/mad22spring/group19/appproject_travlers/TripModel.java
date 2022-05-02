package dk.au.mad22spring.group19.appproject_travlers;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TripModel implements Serializable {

    //Widgets:
    public String cityName, countryName, travelPlanNotes, travelJournalNotes;
    public float travelUserRating, cityTimeZone;
    public boolean userVisitedCity;

    //Primary key for database
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Empty constructor
    public TripModel(){}

    //Constructor
    public TripModel(String cityName, String countryName, String travelPlanNotes, String travelJournalNotes, float travelUserRating, float cityTimeZone, boolean userVisitedCity){
        this.cityName = cityName;
        this.countryName = countryName;
        this.travelPlanNotes = travelPlanNotes;
        this.travelJournalNotes = travelJournalNotes;
        this.travelUserRating = travelUserRating;
        this.cityTimeZone = cityTimeZone;
        this.userVisitedCity = userVisitedCity;
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

    public float getCityTimeZone() {
        return cityTimeZone;
    }

    public void setCityTimeZone(float cityTimeZone) {
        this.cityTimeZone = cityTimeZone;
    }

    public boolean isUserVisitedCity() {
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
}
