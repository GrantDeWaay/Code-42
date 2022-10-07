package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.iastate.code42.databinding.ActivityBaseDrawerBinding;
import edu.iastate.code42.databinding.ActivityDashboardBinding;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseDrawer;

public class DashboardActivity extends BaseDrawer {

    TextView helloMessage;
    ActivityDashboardBinding activityBaseDrawerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("Dashboard");

        User user = User.get(getApplicationContext());

        helloMessage = activityBaseDrawerBinding.getRoot().findViewById(R.id.helloMessage);
        helloMessage.setText("Hello, " + user.getFirstName() + "!");
    }
}