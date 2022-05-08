package dk.au.mad22spring.group19.appproject_travlers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

import dk.au.mad22spring.group19.appproject_travlers.API.CityAPI;
import dk.au.mad22spring.group19.appproject_travlers.Models.TripModel;
import dk.au.mad22spring.group19.appproject_travlers.Models.User;

//Code references:
//Lecture 8: Demo - Tracker

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
    private static MutableLiveData<Boolean> userLoggedIn;
    private static MutableLiveData<Boolean> userCreated;
    private static MutableLiveData<List<TripModel>> tripsDb;


    //Constructor
    public Repository(Context context){

        //Connections:
        cityAPI = new CityAPI(this, context);
        executor = Executors.newSingleThreadExecutor();
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("cities");
        dbRefUser = db.getReference("user");
        trips = new MutableLiveData<>();
        tripsModels = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        userLoggedIn = new MutableLiveData<>();
        userCreated = new MutableLiveData<>();
        tripsDb = new MutableLiveData<>();

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

        DatabaseReference getCities = dbRefUser.child(mAuth.getUid()).child("cities");

        getCities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tripsModels.clear();
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
                city.key = dbRefUser.push().getKey();
                DatabaseReference cities = dbRefUser.child(mAuth.getUid()).child("cities").child(city.key);
                cities.setValue(city);
            }
        });
    }

    //DB: Update specific trip
    public void updateTrip(TripModel city){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                DatabaseReference cities = dbRefUser.child(mAuth.getUid()).child("cities").child(city.key);
                cities.setValue(city);
            }
        });
    }

    //DB: Delete specific trip
    public void deleteTrip(TripModel city){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                DatabaseReference cityToBeDeleted = dbRefUser.child(mAuth.getUid()).child("cities").child(city.key);
                cityToBeDeleted.removeValue();
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
        if (mAuth != null){
            modelTrips = this.getTripsDB();
            Random value = new Random();
            int randomTrip = value.nextInt(modelTrips.getValue().size());
            return modelTrips.getValue().get(randomTrip);
        }

        return null;
    }

   //DB: Login
    public static MutableLiveData<Boolean> didUserLoggedIn(){return userLoggedIn;}
    public void Login(String email, String password, Activity activity){

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Login successful");
                    userLoggedIn.postValue(true);
                    Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Login failed: ", task.getException());
                    Toast.makeText(activity, "Failed to login!", Toast.LENGTH_SHORT).show();
                    userLoggedIn.postValue(false);
                }
            }
        });
    }

    //DB: create account
    public static MutableLiveData<Boolean> didUserGetCreated(){return userCreated;}
    public void createUser(User user, String password, Activity activity){

        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)       //call to create a new user and set callbacks
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.d(TAG, "Account created");
                            userCreated.postValue(true);
                            //String keyId = dbRefUser.push().getKey();
                            dbRefUser.child(mAuth.getUid()).setValue(user);

                        } else {
                            Log.d(TAG, "Could not create account", task.getException());
                            userCreated.postValue(false);
                        }
                    }
                });
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

    //DB: Logout
    public void logout(){
        mAuth.signOut();
    }
}
