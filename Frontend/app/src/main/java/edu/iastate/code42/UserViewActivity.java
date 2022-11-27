package edu.iastate.code42;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.iastate.code42.databinding.ActivityUserViewBinding;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseBack;

/**
 * UserViewActivity class
 * Screen to view and edit details about individual User
 * Layout: activity_user_view
 * Extends BaseDrawer
 * @author Andrew
 */
public class UserViewActivity extends BaseBack {
    ActivityUserViewBinding activityBaseDrawerBinding;

    EditText firstName;
    EditText lastName;
    EditText username;
    EditText email;

    FloatingActionButton edit;
    Button delete;

    User user;
    SharedPreferences userSession;

    boolean viewState;
    int courseId;

    /**
     * Creates and draws the view; initializes the objects
     * Performs GET_USER HTTP Request
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityUserViewBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!userSession.contains("token")){
            Intent login = new Intent(UserViewActivity.this, MainActivity.class);
            startActivity(login);
        }
    }

    private void updateViewState(Boolean state){
        if(state){
            setSave(false);

            edit.setImageDrawable(getDrawable(R.drawable.ic_edit_foreground));
        }else{
            setSave(true);

            edit.setImageDrawable(getDrawable(R.drawable.ic_save_foreground));
        }
    }
}