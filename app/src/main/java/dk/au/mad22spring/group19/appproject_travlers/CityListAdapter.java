package dk.au.mad22spring.group19.appproject_travlers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Code references:
//Lecture 3: Demo 2 - RecyclerView in action
//Clear method: https://stackoverflow.com/questions/29978695/remove-all-items-from-recyclerview

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {

    private ICityClickedListener listener;
    private ArrayList<TripModel> trips;

    //Constructor
    public CityListAdapter(ArrayList<TripModel> tripModels, ICityClickedListener listener){
        this.trips = tripModels;
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
        holder.txtCityName.setText(trips.get(position).getCityName());
        holder.txtCountryName.setText(trips.get(position).getCountryName());
    }

    //The number of items we want to display in recyclerView
    @Override
    public int getItemCount() {
        if(trips == null){
            return 0;
        }
        return trips.size();
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
        trips = tripModels;
        notifyDataSetChanged();
    }

    //Clears ryclerView
    public void clearRecyclerView(){
        if(trips.size() != 0){

            for (int i = 0; i < trips.size(); i++) {
                trips.remove(0);
            }

            notifyItemRangeRemoved(0, trips.size());
        }
        return;
    }
}
