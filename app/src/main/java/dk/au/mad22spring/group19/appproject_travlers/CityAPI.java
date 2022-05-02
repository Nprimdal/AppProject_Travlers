package dk.au.mad22spring.group19.appproject_travlers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

public class CityAPI implements Serializable {

    //References:
    private Repository repository;
    private Context context;
    private MutableLiveData<ArrayList<TripModel>> tripsModelList;
    private ArrayList<TripModel> tripModels = new ArrayList<>();
    private RequestQueue queue;
    private static final String TAG = "CityAPI";

    public CityAPI(Repository repository, Context context){
        this.repository = repository;
        this.context = context;
    }

    //Gets cities from api by name
    public MutableLiveData<ArrayList<TripModel>> getCitiesByName(String cityName){

        tripsModelList.postValue(new ArrayList<>());
        loadCities(cityName);
        return tripsModelList;
    }

    //Returns
    public MutableLiveData<ArrayList<TripModel>>returnTrips() {

        return tripsModelList;
    }

    //Calls the base url for loading drink data from API by name
    public void loadCities(String cityName){
        String baseUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=a98c6ec87e41a4e73b8f9f9daa3e170f&units=metric";
        sendRequest(baseUrl, false);
    }

    //Sends request via volley
    private void sendRequest(String url, boolean onStartUp){
        if(queue == null){
            queue = Volley.newRequestQueue(TripApplication.getAppContext());
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        parseJson(response, onStartUp);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"onErrorResponse",error);
                Toast.makeText(context, "Something went wrong. Unable to find drink.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    //Parse city objects to Json
    private void parseJson(String json, boolean onStartUp) {
        Gson gson = new GsonBuilder().create();
        CityDTO cityInfo = gson.fromJson(json, CityDTO.class);

        if (cityInfo != null){
            TripModel trip = new TripModel();
            trip.setCityName(cityInfo.getName());
            trip.setCountryName(cityInfo.getSys().getCountry());
            trip.setCityTimeZone(cityInfo.getTimezone());

            if (onStartUp) {
                repository.addTripAsynch(trip);
                Log.d(TAG, "parseJson: created startup drinks");
            } else {
                tripModels.add(trip);
                Log.d(TAG, "parseJson: Added drink to search list");
            }
        }
        else {
            Toast.makeText(context, "INFO TEXT=!==!.", Toast.LENGTH_LONG).show();

        }

        returnTrips().postValue(tripModels);
    }
}

