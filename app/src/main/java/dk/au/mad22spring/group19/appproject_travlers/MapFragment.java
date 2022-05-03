package dk.au.mad22spring.group19.appproject_travlers;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

//Reference: https://www.youtube.com/watch?v=YCFPClPjDIQ
//           Lecture: 9

public class MapFragment extends Fragment implements OnMapReadyCallback, TripListAdapter.ITripClickedListener {


    private TripViewModel tripViewModel;
    private GoogleMap mMap;
    private Button btnMap;
    private ImageButton  btnSatellit, btnNormal, btnTerrain, imBtnMap;
    private int mapType = GoogleMap.MAP_TYPE_NORMAL;
    private List<TripModel> trips;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_map, container, false);


        //Set up view model
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);


        tripViewModel.getTrips().observe(getViewLifecycleOwner(), new Observer<List<TripModel>>() {
            @Override
            public void onChanged(List<TripModel> tripModels) {
                trips = tripModels;

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);


        imBtnMap = (ImageButton)  view.findViewById(R.id.imBtnMap);
        imBtnMap.setVisibility(view.getVisibility());
        imBtnMap.bringToFront();
        imBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMapTypeDialog();
            }
        });

        return view;



    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap= googleMap;

        if(trips!=null && trips.size()>0){

            TripModel tripModel;
            LatLngBounds bounds;
            LatLngBounds.Builder allExercisePlaces = new LatLngBounds.Builder();
            for(int i=0; i<trips.size(); i++) {
                tripModel = trips.get(i);
                //calc bounding box
                allExercisePlaces.include(new LatLng(tripModel.getLat(), tripModel.getLon()));

                //add markers

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(tripModel.getLat(), tripModel.getLon()))
                        .title(tripModel.getCityName())
                        .snippet(tripModel.getCountryName())
                );

            }
            bounds = allExercisePlaces.build();

            //use bounding box to zoom properly
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
        }


    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {
            ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap)).getMapAsync(this);  //this is the new way
        }
    }

    private void ShowMapTypeDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.requestWindowFeature(R.id.layMapType);
        View view  = getActivity().getLayoutInflater().inflate(R.layout.map_type_dialog, null);
        dialog.setContentView(view);
        dialog.setContentView(R.layout.map_type_dialog);


        btnSatellit = (ImageButton) dialog.findViewById(R.id.btnTerrain2);
        btnSatellit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapType = GoogleMap.MAP_TYPE_TERRAIN;
                dialog.cancel();
                Log.d("Hej", "Terrain");
                mMap.setMapType(mapType);
            }
        });

        btnNormal = (ImageButton) dialog.findViewById(R.id.btnNormal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapType = GoogleMap.MAP_TYPE_NORMAL;
                dialog.cancel();
                Log.d("Hej", "Normal");
                mMap.setMapType(mapType);
            }
        });

        btnTerrain = (ImageButton) dialog.findViewById(R.id.btnSatallit2);
        btnTerrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapType = GoogleMap.MAP_TYPE_SATELLITE;
                dialog.cancel();
                Log.d("Hej", "Satellit");
                mMap.setMapType(mapType);
            }

        });

        mMap.setMapType(mapType);
        dialog.show();


    }

    @Override
    public void onCityClicked(int position) {

    }
}