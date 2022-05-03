package dk.au.mad22spring.group19.appproject_travlers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Code references:
//Lecture 3: Demo 2 - RecyclerView in action
//Clear method: https://stackoverflow.com/questions/29978695/remove-all-items-from-recyclerview

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    private ITripClickedListener listener;
    private List<TripModel> trips;

    //Constructor
    public TripListAdapter(List<TripModel> tripModels, ITripClickedListener listener){
        this.trips = tripModels;
        this.listener = listener;

    }

    //Provides the layout to the rows in recyclerView
    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_trip, parent, false);

        return new TripViewHolder(view, listener);
    }


    //Sets data on the views
    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        holder.txtCityName.setText(trips.get(position).getCityName());
        holder.txtCountryName.setText(trips.get(position).getCountryName());
        holder.userRating.setRating(trips.get(position).getTravelUserRating());
        //holder.cityVisited.setChecked(trips.get(position).userVisitedCity);
    }

    //The number of items we want to display in recyclerView
    @Override
    public int getItemCount() {
        if(trips == null){
            return 0;
        }
        return trips.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ITripClickedListener listener;

        //References to views
        TextView txtCityName, txtCountryName;
        RatingBar userRating;
        CheckBox cityVisited;

        //Constructor: gets references to view
        public TripViewHolder(@NonNull View itemView, ITripClickedListener listener) {
            super(itemView);

            this.listener = listener;

            txtCityName = itemView.findViewById(R.id.txtCityName);
            txtCountryName = itemView.findViewById(R.id.txtCountryName);
            userRating = itemView.findViewById(R.id.rtnStarsOverview);
            cityVisited = itemView.findViewById(R.id.checkBoxVisited);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onCityClicked(getAdapterPosition());

        }
    }

    //User clicks in recyclerView
    public interface ITripClickedListener {
        void onCityClicked(int position);
    }

    //Data changed
    public void updateCityModel(List<TripModel> tripModels){
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
