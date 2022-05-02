package dk.au.mad22spring.group19.appproject_travlers;

import android.content.Context;

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
    private LiveData<List<TripModel>> trips; //Part of singleton pattern
    private static Repository repository;
    private ExecutorService executor; //Part of background processing

    //Constructor
    private Repository(Context context){

        //Connections:
        tripDatabase = TripDatabase.getDatabase(context);
        cityAPI = new CityAPI(this, context);
        executor = Executors.newSingleThreadExecutor();

    }

    public static Repository getInstance(){
        if (repository==null){
            repository = new Repository(TripApplication.getAppContext());
        }

        return repository;
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
    //Adds a trip to database
    public void addCityAsynch(TripModel city){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tripDatabase.tripDAO().addTrip(city);
            }
        });
    }

}
