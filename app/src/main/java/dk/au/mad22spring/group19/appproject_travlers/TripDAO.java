/*
package dk.au.mad22spring.group19.appproject_travlers;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TripDAO {
    //DAO (Data Access Object): This class provides the methods used to interact with data in the Trip-table.

    @Query("SELECT * FROM TripModel")
    LiveData<List<TripModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTrip(TripModel trip);

    @Query("SELECT Count() FROM TripModel WHERE cityName LIKE :name")
    int findNumberOfCities(String name);

    @Update
    void updateTrip(TripModel tripModel);
}
*/
