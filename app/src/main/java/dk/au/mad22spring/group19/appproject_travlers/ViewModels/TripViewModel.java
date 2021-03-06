package dk.au.mad22spring.group19.appproject_travlers.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad22spring.group19.appproject_travlers.Repository;
import dk.au.mad22spring.group19.appproject_travlers.Models.TripModel;

public class TripViewModel extends ViewModel {

    private Repository repository;
    private LiveData<TripModel> currentSelection;

    //Constructor
    public TripViewModel(){

        repository = Repository.getInstance();
        currentSelection = repository.getCurrentSelection();
    }

    public boolean getCityExists(TripModel tripModel){
        return repository.cityExists(tripModel);
    }

    //Gets selected city/trip
    public LiveData<TripModel> getCurrentSelection() {
        return currentSelection;
    }

    //Sets selected city/trip
    public void updateCurrentSelection(TripModel tripModel) { repository.setCurrentSelection(tripModel); }

    //Gets cities from API by name
    public LiveData<ArrayList<TripModel>> getCities(String cityName) { return repository.getCities(cityName); }

    //DB commands
    public LiveData<List<TripModel>> getTripsDB() { return repository.getTripsDB(); }
    public void addCityDB(TripModel city){ repository.addCity(city); }
    public void updateTripDB(TripModel trip){ repository.updateTrip(trip); }
    public void deleteTripDB(TripModel trip) {repository.deleteTrip(trip);}
    public void updatePasswordDB(String newPassword){repository.updatePassword(newPassword);}
    public void logout() {repository.logout();}




}
