package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseDrawer;

public class DashboardActivity extends BaseDrawer {

    TextView helloMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        User user = User.get(getApplicationContext());
        helloMessage = findViewById(R.id.helloMessage);

        helloMessage.setText("Hello, " + user.getFirstName() + "!");
    }
}