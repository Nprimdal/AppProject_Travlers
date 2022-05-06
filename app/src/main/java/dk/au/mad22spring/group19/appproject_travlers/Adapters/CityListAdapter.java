package dk.au.mad22spring.group19.appproject_travlers.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dk.au.mad22spring.group19.appproject_travlers.R;
import dk.au.mad22spring.group19.appproject_travlers.Models.TripModel;

//Code references:
//Lecture 3: Demo 2 - RecyclerView in action
//Clear method: https://stackoverflow.com/questions/29978695/remove-all-items-from-recyclerview

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {

    private ICityClickedListener listener;
    private ArrayList<TripModel> cities;

    //Constructor
    public CityListAdapter(ArrayList<TripModel> tripModels, ICityClickedListener listener){
        this.cities = tripModels;
        this.listener = listener;

    }

    //Provides the layout to the rows in recyclerView
    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_city, parent, false);

        return new CityViewHolder(view, listener);
    }


    //Sets data on the views
    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.txtCityName.setText(cities.get(position).getCityName());
        holder.txtCountryName.setText(cities.get(position).getCountryName());
    }

    //The number of items we want to display in recyclerView
    @Override
    public int getItemCount() {
        if(cities == null){
            return 0;
        }
        return cities.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ICityClickedListener listener;

        //References to views
        TextView txtCityName, txtCountryName;

        //Constructor: gets references to view
        public CityViewHolder(@NonNull View itemView, ICityClickedListener listener) {
            super(itemView);

            this.listener = listener;

            txtCityName = itemView.findViewById(R.id.txtCityName);
            txtCountryName = itemView.findViewById(R.id.txtCountryName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onCityClicked(getAdapterPosition());

        }
    }

    //User clicks in recyclerView
    public interface ICityClickedListener {
        void onCityClicked(int position);
    }

    //Data changed
    public void updateCityModel(ArrayList<TripModel> tripModels){
        cities = tripModels;
        notifyDataSetChanged();
    }

    //Clears ryclerView
    public void clearRecyclerView(){

           for (int i = 0; i < cities.size(); i++) {

           cities.remove(0);
               Log.d("HEJ", "Loop ");

           }
            notifyItemRangeRemoved(0, cities.size());


        return;
    }
}
