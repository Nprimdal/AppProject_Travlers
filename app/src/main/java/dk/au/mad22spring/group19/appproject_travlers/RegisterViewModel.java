package dk.au.mad22spring.group19.appproject_travlers;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel  extends ViewModel {

    private Repository repository;
    private LiveData<Boolean> created;

    public RegisterViewModel() {

        repository = Repository.getInstance();
        created = repository.didUserGetCreated();
    }


    public void createNewAccount(User user, String password, Activity activity){
        repository.createUser(user, password, activity);
    }

    public LiveData<Boolean> userCreated() {return created;}
}
