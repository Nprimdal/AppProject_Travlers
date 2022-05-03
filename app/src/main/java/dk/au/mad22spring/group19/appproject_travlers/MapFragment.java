package dk.au.mad22spring.group19.appproject_travlers;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Reference: https://www.youtube.com/watch?v=YCFPClPjDIQ
//           Lecture: 9

public class MapFragment extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    private Button btnMap, btnSatellit, btnHybrid, btnTa;
    private int mapType = GoogleMap.MAP_TYPE_NORMAL;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_map, container, false);



        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);

        btnMap = (Button) view.findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
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

        LatLng coor = new LatLng(33.9528, 6.0132);
        mMap.addMarker(new MarkerOptions().position(coor).title("new coor"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coor));

    }

    private void ShowMapTypeDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.map_type_dialog, null);
        dialog.setContentView(view);

        //btnSatellit = (Button) view.findViewById(R.id.btnSatellit)

        String s = "";
        if(mapType==GoogleMap.MAP_TYPE_SATELLITE){
            mapType = GoogleMap.MAP_TYPE_HYBRID;
            s = "Hybrid";
        } else if(mapType==GoogleMap.MAP_TYPE_HYBRID){
            mapType = GoogleMap.MAP_TYPE_TERRAIN;
            s = "Terrain";
        } else if(mapType==GoogleMap.MAP_TYPE_TERRAIN){
            mapType = GoogleMap.MAP_TYPE_NORMAL;
            s = "Normal";
        } else if(mapType==GoogleMap.MAP_TYPE_NORMAL){
            mapType = GoogleMap.MAP_TYPE_NONE;
            s = "None";
        } else if(mapType==GoogleMap.MAP_TYPE_NONE){
            mapType = GoogleMap.MAP_TYPE_SATELLITE;
            s = "Satellite";
        }

        mMap.setMapType(mapType);
        //Toast.makeText(getApplicationContext(), "Maptype changed to " + s, Toast.LENGTH_SHORT).show();

    }
}