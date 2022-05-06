package dk.au.mad22spring.group19.appproject_travlers.API;

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

import dk.au.mad22spring.group19.appproject_travlers.Models.TripModel;
import dk.au.mad22spring.group19.appproject_travlers.Repository;

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

        tripsModelList = new MutableLiveData<>(new ArrayList<>());
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

    //Loads city data from base url
    public void loadCities(String cityName){
        //String baseUrl = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=a98c6ec87e41a4e73b8f9f9daa3e170f&units=metric";
        String baseUrl = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=a98c6ec87e41a4e73b8f9f9daa3e170f&units=metric";
        sendRequest(baseUrl);
    }

    //Sends request via volley
    private void sendRequest(String url){
        if(queue == null){
            queue = Volley.newRequestQueue(context);
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"onErrorResponse",error);
                Toast.makeText(context, "Something went wrong. Unable to find city.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    //Parse city objects to Json
    private void parseJson(String json) {
        Gson gson = new GsonBuilder().create();
        CityDTO cityInfo = gson.fromJson(json, CityDTO.class);

        if (cityInfo != null){
            TripModel trip = new TripModel();
            trip.setCityName(cityInfo.getName());
            trip.setCountryName(cityInfo.getSys().getCountry());
            trip.setLat(cityInfo.getCoord().getLat());
            trip.setLon(cityInfo.getCoord().getLon());

                tripModels.add(trip);
                Log.d(TAG, "parseJson: added drink to search list");
        }
        else {
            Toast.makeText(context, "Was unable to retrieve city information.", Toast.LENGTH_LONG).show();

        }

        returnTrips().postValue(tripModels);
    }
}

