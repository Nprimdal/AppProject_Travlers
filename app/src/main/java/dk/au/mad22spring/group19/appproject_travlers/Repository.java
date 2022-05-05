package dk.au.mad22spring.group19.appproject_travlers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    //Constants
    public static final String TAG = "AUTH";

    //References:
    private CityAPI cityAPI;
    private MutableLiveData<List<TripModel>> trips;
    private LiveData<List<TripModel>> modelTrips;
    private ArrayList<TripModel> tripsModels;
    private static MutableLiveData<TripModel> currentSelection;
    private static Repository repository; //Part of singleton pattern
    private ExecutorService executor; //Part of background processing
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private DatabaseReference dbRefUser;
    private FirebaseAuth mAuth;

    //Constructor
    public Repository(Context context){

        //Connections:
        cityAPI = new CityAPI(this, context);
        executor = Executors.newSingleThreadExecutor();
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("cities");
        modelTrips = this.getTripsDB();
        dbRefUser = db.getReference("user");
        trips = new MutableLiveData<>();
        tripsModels = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

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

    //API: Get cities by name
    public LiveData<ArrayList<TripModel>> getCities(String cityName){
        return cityAPI.getCitiesByName(cityName);
    }

   //DB: Get all trips
    public LiveData<List<TripModel>> getTripsDB(){


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();
                while (snapshots.iterator().hasNext()){
                    tripsModels.add(snapshots.iterator().next().getValue(TripModel.class));
                }
                trips.postValue(tripsModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return trips;
    }



    //DB: Add a trip
    public void addCity(TripModel city){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                city.key = dbRef.push().getKey();
                dbRef.child(city.key).setValue(city);
            }
        });
    }

    //DB: Update specific trip
    public void updateTrip(TripModel city){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dbRef.child(city.key).setValue(city);
            }
        });
    }

    //DB: Delete specific trip
    public void deleteTrip(TripModel city){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dbRef.child(city.key).removeValue();
            }
        });
    }

    public boolean cityExists(TripModel trip){

        ArrayList<Integer> citiesSameLocation = new ArrayList<>();
        boolean cityExists = false;

        for (TripModel tripModel: tripsModels)
        {
            if(tripModel.lat == trip.lat && tripModel.lon == trip.lon)
            {
                citiesSameLocation.add(1);

            }

        }

        if(citiesSameLocation.size() > 0)
        {
            cityExists = true;
        }

        return cityExists;
    }


    public TripModel randomTrips(){
        Random value = new Random();
        int randomTrip = value.nextInt(modelTrips.getValue().size());
        return modelTrips.getValue().get(randomTrip);

    }

    //DB: Update password
    public void updatePassword(String newPassword){
        FirebaseUser user = mAuth.getCurrentUser();

        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User password updated.");
                }
            }
        });
    }
}
