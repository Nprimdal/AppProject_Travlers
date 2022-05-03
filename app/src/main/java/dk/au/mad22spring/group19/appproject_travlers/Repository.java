package dk.au.mad22spring.group19.appproject_travlers;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    //References:
    private TripDatabase tripDatabase;
    private CityAPI cityAPI;
    private LiveData<List<TripModel>> trips;
    private static MutableLiveData<TripModel> currentSelection = new MutableLiveData<>();
    private static Repository repository; //Part of singleton pattern
    private ExecutorService executor; //Part of background processing

    //Constructor
    private Repository(Context context){

        //Connections:
        tripDatabase = TripDatabase.getDatabase(context);
        cityAPI = new CityAPI(this, context);
        executor = Executors.newSingleThreadExecutor();

        //Retrieves trips from database
        trips = tripDatabase.tripDAO().getAll();

    }

    public static Repository getInstance(){
        if (repository==null){
            repository = new Repository(TripApplication.getAppContext());
        }

        return repository;
    }

    //Gets current selected item
     public static MutableLiveData<TripModel> getCurrentSelection(){
        if(currentSelection == null){
            currentSelection = new MutableLiveData<TripModel>();
        }
        return currentSelection;
    }

    //Sets current selected item
    public static void setCurrentSelection(TripModel tripModel){
        currentSelection.postValue(tripModel);
    }

    //Get cities by name from API
    public LiveData<ArrayList<TripModel>> getCities(String cityName){
        return cityAPI.getCitiesByName(cityName);
    }

    //Checks how many cities with a specific name exists in db
    public LiveData<Integer> getNumberOfSpecificCity(TripModel city){
        MutableLiveData<Integer> data = new MutableLiveData();
        executor.execute(() -> {int count = tripDatabase.tripDAO().findNumberOfCities(city.cityName);
            data.postValue(count);
        });

        return data;
    }

    //Add a trip to database
    public void addCityAsynch(TripModel city){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tripDatabase.tripDAO().addTrip(city);
            }
        });
    }

    //Update trip in database
    public void updateTripAsynch(TripModel trip){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tripDatabase.tripDAO().updateTrip(trip);
            }
        });
    }

    //Get trips from database
    public LiveData<List<TripModel>> getAllTrips(){

        return trips;
    }

}
