package dk.au.mad22spring.group19.appproject_travlers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchFragment extends Fragment  implements CityListAdapter.ICityClickedListener {

    //References:
    private RecyclerView rcv;
    private CityListAdapter cityListAdaptor;
    private ArrayList<TripModel> trips = new ArrayList<>();
    private TripViewModel tripViewModel;
    private EditText edtSearchCity;
    private Button btnSearch;
    String searchString;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //Sets up view model
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        //Set up adapter and recyclerView
        cityListAdaptor = new CityListAdapter(trips, this);

        rcv = (RecyclerView) view.findViewById(R.id.rcvCities);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(cityListAdaptor);

        //Search input field
        edtSearchCity = (EditText) view.findViewById(R.id.edtSearchField);

        //Search button
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchString = edtSearchCity.getText().toString();
                searchCity(searchString);
                cityListAdaptor.clearRecyclerView();

            }
        });

        return view;
    }

    //Gets cities and calls update on recyclerView
    private void searchCity(String searchString) {
        tripViewModel.getCities(searchString).observe(this, tripVM ->{
            trips = tripVM;
            cityListAdaptor.updateCityModel(trips);
        });

    }

    //Adds city to database
    @Override
    public void onCityClicked(int position) {

      /*  tripViewModel.getNumberOfCity(trips.get(position)).observe(this, integer -> {
            if (integer > 0){

                Toast.makeText(getContext(), "City already exists in your travel overview.", Toast.LENGTH_SHORT).show();
            }
            else {
                //tripViewModel.addCity(trips.get(position));
                Toast.makeText(getContext(), "City was successfully added to your travel overview", Toast.LENGTH_SHORT).show();
            }
        });*/

        if(tripViewModel.getCityExists(trips.get(position)))
        {
            Toast.makeText(getContext(), "City already exists in your travel overview.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            tripViewModel.addCityDB(trips.get(position));
            Toast.makeText(getContext(), "City was successfully added to your travel overview", Toast.LENGTH_SHORT).show();
        }

    }
}