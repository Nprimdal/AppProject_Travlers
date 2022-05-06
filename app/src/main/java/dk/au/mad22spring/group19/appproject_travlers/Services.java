package dk.au.mad22spring.group19.appproject_travlers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad22spring.group19.appproject_travlers.Models.TripModel;


//Inspiration from lesson 5 Services and Async processing
public class Services extends Service {
    private static final String TAG = "ForegroundService";          //tag for logging
    public static final String SERVICE_CHANNEL = "serviceChannel";  //Notification channel name
    public static final int NOTIFICATION_ID = 42;


    ExecutorService execService;    //ExecutorService for running things off the main thread
    private static Repository repository;
    boolean started = false;        //indicating if Service is startet
    int sleepTime = 10000;

    //empty constructor
    public Services() {
    }

    //onCreate called before onStartCommand when Service first created
    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(TripApplication.getAppContext());
    }

    //onStartCommand called when an Actvity starts the Service with Intent through calling startService(...)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand");

        //check for Android version - whether we need to create a notification channel (from Android 0 and up, API 26)
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(SERVICE_CHANNEL, "Foreground Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        //build the notification
        Notification notification = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                .setContentTitle("Find your next trip in the App")
                .setSmallIcon(R.drawable.ic_baseline_flight_24)
                .build();

        startForeground(NOTIFICATION_ID, notification);


        //this method starts recursive background work
        doBackgroundStuff();

        //returning START_STICKY will make the Service restart again eventually if it gets killed off (e.g. due to resources)
        return START_STICKY;
    }

    //if Service is destroyed
    @Override
    public void onDestroy() {
        started = false;
        super.onDestroy();
    }

    //initate the background work - only start if not already started
    private void doBackgroundStuff() {
        if(!started) {
            started = true;
            doRecursiveWork();
        }
    }


    private void doRecursiveWork(){
        if(execService == null){
            execService = Executors.newSingleThreadExecutor();
        }

        execService.submit(new Runnable() {
            @Override
            public void run(){
                Log.d(TAG,"Run started");
                try{
                    Thread.sleep(sleepTime); //This is executed every 60 seconds
                } catch (InterruptedException e){
                    Log.e(TAG,"run: ERROR", e);
                }

                TripModel tripModel = repository.randomTrips();

                if (tripModel!= null) {
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), SERVICE_CHANNEL)
                            .setContentTitle("" + "Today's random trip: " + tripModel.cityName)
                            .setContentText("Country: " + tripModel.countryName)
                            .setSmallIcon(R.drawable.ic_baseline_flight_24)
                            .build();
                    NotificationManager man = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    man.notify(NOTIFICATION_ID, notification);
                }

                if(started){
                    doRecursiveWork(); //Calls it self to keep the method running
                }
            }
        });
    }

    //This is not a bound service, so we return null
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
