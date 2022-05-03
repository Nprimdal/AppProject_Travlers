package dk.au.mad22spring.group19.appproject_travlers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class TripDetailsFragment extends Fragment implements OnMapReadyCallback {

    private TripViewModel tripViewModel;
    private TextView txtCityName, txtCountryName;
    private RatingBar rtnUserRating;
    private EditText edtTravelPlan, edtTravelJournal;
    private TripModel trip;
    private Button btnSave, btnBack, btnDelete;
    private CheckBox checkBoxCityVisited;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);

        //Set up view model
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel.getCurrentSelection().observe(getViewLifecycleOwner(), new Observer<TripModel>() {
                    @Override
                    public void onChanged(TripModel tripModel) {
                        trip = tripModel;
                        
                        updateTripDetailsUI();
                    }
        });

        //Get views
        txtCityName = (TextView) view.findViewById(R.id.txtCityNameDetails);
        txtCountryName = (TextView) view.findViewById(R.id.txtCountryNameDetails);
        rtnUserRating = (RatingBar) view.findViewById(R.id.rtnUserRatingDetails);
        edtTravelPlan = (EditText) view.findViewById(R.id.txtTravelPlan);
        edtTravelJournal = (EditText) view.findViewById(R.id.txtTravelJournal);
        checkBoxCityVisited = (CheckBox) view.findViewById(R.id.checkBoxVisited);

        //Set up save button
        btnSave = (Button) view.findViewById(R.id.btnSaveTrip);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTripChanges();
            }
        });

        //Set up back button
        btnBack = (Button) view.findViewById(R.id.btnBackTripDetails);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { back(); }
        });

        //Set up delete button

//      SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
//        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


    }

    private void updateTripDetailsUI() {

        //Set trip data
        txtCityName.setText(trip.getCityName());
        txtCountryName.setText(trip.getCountryName());
        rtnUserRating.setNumStars((int) trip.getTravelUserRating());
        edtTravelPlan.setText(trip.getTravelPlanNotes());
        edtTravelJournal.setText(trip.getTravelJournalNotes());
        checkBoxCityVisited.setChecked(trip.hasUserVisitedCity());

    }

    private void saveTripChanges(){
        trip.setTravelUserRating(rtnUserRating.getRating());
        trip.setTravelPlanNotes(edtTravelPlan.getText().toString());
        trip.setTravelJournalNotes(edtTravelJournal.getText().toString());
        trip.setUserVisitedCity(checkBoxCityVisited.isChecked());
        tripViewModel.updateTrip(trip);
    }

    private void deleteTrip(){

    }

    private void back(){

    }
}