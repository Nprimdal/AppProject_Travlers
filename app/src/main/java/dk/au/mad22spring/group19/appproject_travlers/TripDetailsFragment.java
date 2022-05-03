package dk.au.mad22spring.group19.appproject_travlers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class TripDetailsFragment extends Fragment implements OnMapReadyCallback {

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

  //      SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
//        mapFragment.getMapAsync(this);
        return inflater.inflate(R.layout.fragment_trip_details, container, false);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


    }
}