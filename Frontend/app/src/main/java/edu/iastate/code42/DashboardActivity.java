package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import edu.iastate.code42.databinding.ActivityBaseDrawerBinding;
import edu.iastate.code42.databinding.ActivityDashboardBinding;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseDrawer;

/**
 * DashboardActivity class
 * Dashboard screen
 * Layout: activity_dashboard
 * Extends BaseDrawer
 */
public class DashboardActivity extends BaseDrawer {

    TextView helloMessage;
    ActivityDashboardBinding activityBaseDrawerBinding;
    SharedPreferences userSession;
    User user;

    /**
     * Creates and draws the view; initializes the objects
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("Dashboard");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!userSession.contains("token")){
            Intent login = new Intent(DashboardActivity.this, MainActivity.class);
            startActivity(login);
        }

        helloMessage = activityBaseDrawerBinding.getRoot().findViewById(R.id.helloMessage);
        helloMessage.setText("Hello, " + user.getFirstName() + "!");
    }
}