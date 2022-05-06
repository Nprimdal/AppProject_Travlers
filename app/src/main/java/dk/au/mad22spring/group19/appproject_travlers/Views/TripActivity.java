package dk.au.mad22spring.group19.appproject_travlers.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dk.au.mad22spring.group19.appproject_travlers.R;
import dk.au.mad22spring.group19.appproject_travlers.Repository;
import dk.au.mad22spring.group19.appproject_travlers.Services;
import dk.au.mad22spring.group19.appproject_travlers.Views.HomeFragment;
import dk.au.mad22spring.group19.appproject_travlers.Views.MapFragment;
import dk.au.mad22spring.group19.appproject_travlers.Views.SearchFragment;
import dk.au.mad22spring.group19.appproject_travlers.Views.SettingsFragment;

//Reference: https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/
//Reference: https://www.youtube.com/watch?v=Bb8SgfI4Cm4
public class TripActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        repository = Repository.getInstance();

         bottomNavigationView = findViewById(R.id.bottomNavigationView);
         bottomNavigationView.setOnNavigationItemSelectedListener(this);

         if (savedInstanceState == null){
             bottomNavigationView.setSelectedItemId(R.id.home);
         }

        //Set up the service and its intent
        Intent serviceIntent = new Intent(this, Services.class);
        startService(serviceIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
                return true;

            case R.id.location:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MapFragment()).commit();
                return true;

            case R.id.person:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SettingsFragment()).commit();
                return true;
            case R.id.search:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SearchFragment()).commit();
                return true;
        }
        return false;
    }

}