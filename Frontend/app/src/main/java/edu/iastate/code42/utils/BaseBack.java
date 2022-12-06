package edu.iastate.code42.utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.CoursesActivity;
import edu.iastate.code42.DashboardActivity;
import edu.iastate.code42.MainActivity;
import edu.iastate.code42.R;
import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.User;

public class BaseBack extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FrameLayout container;
    Intent previousScreen;
    Boolean save;
    PopupWindow saveWindow;
    View saveView;
    Button saveWindowExit;
    Button saveWindowStay;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base_back,null);
        container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        save = false;

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        saveView = inflater.inflate(R.layout.popup_window_save,null);

        saveWindowExit = saveView.findViewById(R.id.saveWindowExitButton);
        saveWindowExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(previousScreen);
                saveWindow.dismiss();
            }
        });

        saveWindowStay = saveView.findViewById(R.id.saveWindowStayButton);
        saveWindowStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWindow.dismiss();
            }
        });

        saveWindow = new PopupWindow(saveView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
    }

    public void setPreviousScreen(Intent previousScreen) {
        this.previousScreen = previousScreen;
    }

    public void setSave(Boolean save) {
        this.save = save;
    }

    protected void allocateActivityTitle(String titleString){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(titleString);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (save) {
                    saveWindow.showAtLocation(drawerLayout.getRootView(), Gravity.CENTER, 0, 0);
                } else {
                    if (previousScreen != null) {
                        startActivity(previousScreen);
                    } else {
                        onBackPressed();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}