package dk.au.mad22spring.group19.appproject_travlers;

import android.app.Application;
import android.content.Context;

//Code references:
//Lecture 8: Demo - Tracker

public class TripApplication extends Application {

    private static TripApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    //Get Application context - ONLY for use outside UI (e.g. for repository)
    public static Context getAppContext(){
        return instance.getApplicationContext();
    }

}
