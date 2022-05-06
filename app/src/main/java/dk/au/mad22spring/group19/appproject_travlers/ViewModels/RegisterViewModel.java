package dk.au.mad22spring.group19.appproject_travlers.ViewModels;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import dk.au.mad22spring.group19.appproject_travlers.Repository;
import dk.au.mad22spring.group19.appproject_travlers.Models.User;

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
