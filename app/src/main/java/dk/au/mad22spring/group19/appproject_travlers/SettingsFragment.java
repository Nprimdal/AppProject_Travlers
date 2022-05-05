package dk.au.mad22spring.group19.appproject_travlers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SettingsFragment extends Fragment {

    private TripViewModel tripViewModel;
    private TripModel trip;
    private User user;
    private TextView txtUserName, txtUserPassword;
    private Button btnLogout;
    private FloatingActionButton fabSave;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragtment_settings, container, false);

        //Set up view model
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel.getCurrentSelection().observe(getViewLifecycleOwner(), new Observer<TripModel>() {
            @Override
            public void onChanged(TripModel tripModel) {
                trip = tripModel;
                txtUserName.setText(user.getFullName());
            }
        });

        //Set up views
        txtUserName = (TextView) view.findViewById(R.id.txtUserName);

        //Set up save button
        fabSave = (FloatingActionButton) view.findViewById(R.id.fabSaveSettings);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });


        return view;

    }


    private void saveSettings() {
        trip.setTravelUserRating(rtnUserRating.getRating());
        trip.setTravelPlanNotes(edtTravelPlan.getText().toString());
        trip.setTravelJournalNotes(edtTravelJournal.getText().toString());
        trip.setUserVisitedCity(checkBoxCityVisited.isChecked());
        tripViewModel.updateTripDB(trip);
    }

}