package dk.au.mad22spring.group19.appproject_travlers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TripViewModel extends ViewModel {

    //References
    private Repository repository;
    private LiveData<TripModel> currentSelection;

    //Constructor
    public TripViewModel(){

        repository = Repository.getInstance();
        currentSelection = repository.getCurrentSelection();
    }

    public LiveData<TripModel> getCurrentSelection() {
        return currentSelection;
    }

    public void updateCurrentSelection(TripModel tripModel) {
        repository.setCurrentSelection(tripModel);
    }

    public LiveData<ArrayList<TripModel>> getCities(String cityName) {

        return repository.getCities(cityName);
    }

    //Gets all drinks from database
    public LiveData<List<TripModel>> getTrips() {

        return repository.getAllTrips();
    }

    /*public LiveData<Integer> getNumberOfCity(TripModel city) {
        return repository.getNumberOfSpecificCity(city);
    }*/


    //public void addCity(TripModel city){ repository.addCityAsynch(city); }

    //public void updateTrip(TripModel trip){repository.updateTripAsynch(trip);}

    public void addCityFirebase(TripModel city){ repository.addCityFirebase(city); }

    public void updatTripFirebase(TripModel city){ repository.updateTripFirebase(city); }


    public LiveData<List<TripModel>> getTripsFirebase() {

        return repository.getAllTripsFirebase();
    }

}
