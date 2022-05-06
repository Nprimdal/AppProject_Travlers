package dk.au.mad22spring.group19.appproject_travlers.Models;

public class TripModel {

    public String cityName, countryName, travelPlanNotes, travelJournalNotes, key;
    public boolean userVisitedCity;
    public double lon, lat;
    public float travelUserRating;

    //Default constructor - required for calls to DataSnapshot.getValue(TripModel.Class)
    public TripModel(){}

    //Constructor
    public TripModel(String key, String cityName, String countryName, String travelPlanNotes, String travelJournalNotes, float travelUserRating, boolean userVisitedCity, double lon, double lat){
        this.cityName = cityName;
        this.countryName = countryName;
        this.travelPlanNotes = travelPlanNotes;
        this.travelJournalNotes = travelJournalNotes;
        this.travelUserRating = travelUserRating;
        this.userVisitedCity = userVisitedCity;
        this.lon = lon;
        this.lat = lat;
        this.key = key;
    }

    //Getters and Setters

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public boolean isUserVisitedCity() {
        return userVisitedCity;
    }

    public void setUserVisitedCity(boolean userVisitedCity) {
        this.userVisitedCity = userVisitedCity;
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

    public float getTravelUserRating() {
        return travelUserRating;
    }

    public void setTravelUserRating(float travelUserRating) {
        this.travelUserRating = travelUserRating;
    }
}
