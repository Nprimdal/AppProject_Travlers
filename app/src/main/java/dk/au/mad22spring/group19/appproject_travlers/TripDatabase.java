package dk.au.mad22spring.group19.appproject_travlers;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Code references:
//Lecture 4: Demo - using Room (and SharedPreferences)

@Database(entities = {TripModel.class}, version = 6)
public abstract class TripDatabase extends RoomDatabase {

    public abstract TripDAO tripDAO(); //get DAO
    private static  TripDatabase dbInstance; //Database instance for singleton pattern

    //Singleton pattern - to avoid creating multiple instances of the db-instance above
    public static TripDatabase getDatabase(final Context context){
        if(dbInstance == null){
            synchronized (TripDatabase.class){
                if (dbInstance == null){
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            TripDatabase.class, "trip_database")
                            .build();
                }
            }
        }
        return dbInstance;
    }
}
