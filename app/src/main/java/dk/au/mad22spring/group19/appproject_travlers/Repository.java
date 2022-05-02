package dk.au.mad22spring.group19.appproject_travlers;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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

    }

    public static Repository getInstance(){
        if (repository==null){
            repository = new Repository(TripApplication.getAppContext());
        }

        return repository;
    }

    //Get cities by name from API
    public LiveData<ArrayList<TripModel>> getDrinks(String cityName){
        return cityAPI.getCitiesByName(cityName);
    }

    //Adds a trip to database
    public void addTripAsynch(TripModel trip){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tripDatabase.tripDAO().addTrip(trip);
            }
        });
    }

}
