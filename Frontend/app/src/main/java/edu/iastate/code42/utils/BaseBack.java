package edu.iastate.code42.utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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

public class BaseBack extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawerLayout;
    Intent previousScreen;
    Boolean save;
    PopupWindow saveWindow;
    View saveView;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base_back,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
    public void onClick(View view) {
        if(save){

        }else{
            if(previousScreen != null){
                startActivity(previousScreen);
            }else{
                onBackPressed();
            }
        }
    }
}