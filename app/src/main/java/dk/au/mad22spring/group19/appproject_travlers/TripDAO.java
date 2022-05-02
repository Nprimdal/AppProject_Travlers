package dk.au.mad22spring.group19.appproject_travlers;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface TripDAO {
    //DAO (Data Access Object): This class provides the methods used to interact with data in the Trip-table.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTrip(TripModel trip);
}
