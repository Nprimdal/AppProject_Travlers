package dk.au.mad22spring.group19.appproject_travlers;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private Repository repository;
    private LiveData<Boolean> loggedIn;


    public LoginViewModel() {
        repository = Repository.getInstance();
        loggedIn = repository.didUserLoggedIn();
    }

    public void Login(String email, String password, Activity activity){
        repository.Login(email, password, activity);
    }

    public LiveData<Boolean> userLoggedIn() {return loggedIn;}
}
