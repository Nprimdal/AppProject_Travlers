package dk.au.mad22spring.group19.appproject_travlers.Views;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import dk.au.mad22spring.group19.appproject_travlers.R;
import dk.au.mad22spring.group19.appproject_travlers.Models.TripModel;
import dk.au.mad22spring.group19.appproject_travlers.ViewModels.TripViewModel;

//References
//ViewModel: Lecture 2 - Demo/live (Clicker app part 2)
//AlertDialog implementation: https://stackoverflow.com/questions/42983407/making-a-confirmation-dialog-box-for-deletion
//Disable save-button if EditView is not changed: https://stackoverflow.com/questions/22680106/how-to-disable-button-if-edittext-is-empty
//Navigate to another fragment: Lecture 8 - Demo (Fragments for dual-pane Master-Detail flow - with communication through Activity)

public class TripDetailsFragment extends Fragment implements OnMapReadyCallback {

    private TripViewModel tripViewModel;
    private TextView txtCityName, txtCountryName;
    private RatingBar rtnUserRating;
    private EditText edtTravelPlan, edtTravelJournal;
    private TripModel trip;
    private CheckBox checkBoxCityVisited;
    private ImageView imgFlight, imgCheckMarK;
    private GoogleMap gMap;
    private FloatingActionButton fabBack, fabDelete, fabSave;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        //Set up views
        txtCityName = (TextView) view.findViewById(R.id.txtCityNameDetails);
        txtCountryName = (TextView) view.findViewById(R.id.txtCountryNameDetails);
        rtnUserRating = (RatingBar) view.findViewById(R.id.rtnUserRatingDetails);
        edtTravelPlan = (EditText) view.findViewById(R.id.txtTravelPlan);
        edtTravelJournal = (EditText) view.findViewById(R.id.txtTravelJournal);
        imgCheckMarK = (ImageView) view.findViewById(R.id.imgCheckMarkDetails);
        imgFlight = (ImageView) view.findViewById(R.id.imgFlightDetails);

        //Set up save button
        fabSave = (FloatingActionButton) view.findViewById(R.id.fabSaveTripDetails);
        fabSave.setEnabled(false);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTripChanges();
            }
        });


        //Set up back button
        fabBack = (FloatingActionButton) view.findViewById(R.id.fabBackTripDetails);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        //Set up delete button
        fabDelete = (FloatingActionButton) view.findViewById(R.id.fabDeleteDetails);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTrip(trip);
            }
        });

        //Set up checkbox
        checkBoxCityVisited = (CheckBox) view.findViewById(R.id.checkBoxVisited);
        checkBoxCityVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabSave.setEnabled(true);
                if (checkBoxCityVisited.isChecked()){
                    imgFlight.setVisibility(View.GONE);
                    imgCheckMarK.setVisibility(View.VISIBLE);
                }
                else {
                    imgFlight.setVisibility(View.VISIBLE);
                    imgCheckMarK.setVisibility(View.GONE);
                }
            }
        });

        //Set up map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);

        //Check if views have changed
        viewsEdited();

        return view;
    }

    //Update UI with trip data
    private void updateTripDetailsUI() {
        //Set trip data
        txtCityName.setText(trip.getCityName());
        txtCountryName.setText(trip.getCountryName());
        rtnUserRating.setRating(trip.getTravelUserRating());
        edtTravelPlan.setText(trip.getTravelPlanNotes());
        edtTravelJournal.setText(trip.getTravelJournalNotes());
        checkBoxCityVisited.setChecked(trip.isUserVisitedCity());

       if (checkBoxCityVisited.isChecked()){
            imgFlight.setVisibility(View.GONE);
        }
       else{
            imgCheckMarK.setVisibility(View.GONE);
        }
    }

    //Save trip
    private void saveTripChanges(){
        trip.setTravelUserRating(rtnUserRating.getRating());
        trip.setTravelPlanNotes(edtTravelPlan.getText().toString());
        trip.setTravelJournalNotes(edtTravelJournal.getText().toString());
        trip.setUserVisitedCity(checkBoxCityVisited.isChecked());
        tripViewModel.updateTripDB(trip);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
    }

    //Delete trip
    private void deleteTrip(@NonNull TripModel trip){

        //Builds and shows Dialog to make user confirm delete
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete " + trip.cityName + " from your library?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //If 'Yes': City is deleted and application returns to HomeFragment
                        tripViewModel.deleteTripDB(trip);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    //If 'No': Dialog is closed
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    private void back(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
    }

    //Set city map marker
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        gMap = googleMap;

        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(trip.getLat(), trip.getLon()))
                .title(trip.getCityName())
                .snippet(trip.getCountryName()));

        if(gMap != null) {
            final LatLng locLatLong = new LatLng(trip.getLat(), trip.getLon());
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locLatLong, 10));
        }

    }

    //Enable save-button when EditView has changed
    private void viewsEdited() {
        ArrayList<EditText> editTexts = new ArrayList();
        editTexts.add(edtTravelJournal);
        editTexts.add(edtTravelPlan);

        for (EditText editText: editTexts){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.toString().trim().length() == 0){
                        fabSave.setEnabled(false);
                    }
                    else{
                        fabSave.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        //Rating change listener
        rtnUserRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                fabSave.setEnabled(true);
            }
        });

    }
}