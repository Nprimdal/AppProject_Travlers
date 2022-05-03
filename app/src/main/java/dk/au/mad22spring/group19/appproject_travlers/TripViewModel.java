package dk.au.mad22spring.group19.appproject_travlers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TripViewModel extends ViewModel {

    //References
    private Repository repository;

    //Constructor
    public TripViewModel(){
        repository = Repository.getInstance();
    }

    public LiveData<ArrayList<TripModel>> getCities(String cityName) {

        return repository.getCities(cityName);
    }

    //Gets all drinks from database
    public LiveData<List<TripModel>> getTrips() {

        return repository.getAllTrips();
    }


    public void addCity(TripModel city){ repository.addCityAsynch(city);
    }

    public LiveData<Integer> findNumberOfCity(TripModel city) {
        return repository.getNumberOfSpecificCity(city);
    }


}
