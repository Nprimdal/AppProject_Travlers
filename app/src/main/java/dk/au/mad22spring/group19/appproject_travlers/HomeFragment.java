package dk.au.mad22spring.group19.appproject_travlers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;


public class HomeFragment extends Fragment implements TripListAdapter.ITripClickedListener {

    private TripViewModel tripViewModel;
    private TripListAdapter tripAdapter;
    private List<TripModel> trips;
    private RecyclerView rcv;
    private Button btnAddCity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Set up view model
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        //Hvad er getViewLifeCycleOwner??????
        tripViewModel.getTrips().observe(getViewLifecycleOwner(), new Observer<List<TripModel>>() {
            @Override
            public void onChanged(List<TripModel> tripModels) {
                trips = tripModels;
                tripAdapter.updateCityModel(trips);
            }
        });

        //Set up adapter and recyclerView
        tripAdapter = new TripListAdapter(trips,this);
        rcv = (RecyclerView) view.findViewById(R.id.rcvTrips);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(tripAdapter);

        //Add button - replaces view with searchFragment
        btnAddCity = (Button) view.findViewById(R.id.btnAddCity);
        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SearchFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onCityClicked(int position) {

    }
}