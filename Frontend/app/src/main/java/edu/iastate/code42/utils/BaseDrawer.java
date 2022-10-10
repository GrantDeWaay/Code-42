package edu.iastate.code42.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import edu.iastate.code42.CoursesActivity;
import edu.iastate.code42.DashboardActivity;
import edu.iastate.code42.MainActivity;
import edu.iastate.code42.R;
import edu.iastate.code42.objects.User;

public class BaseDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    TextView headerName;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base_drawer,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.nav_profile:

                break;
            case R.id.nav_dashboard:
                startActivity(new Intent(this, DashboardActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_courses:
                startActivity(new Intent(this, CoursesActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_grades:

                break;
            case R.id.nav_settings:

                break;
            case R.id.nav_logout:
                SharedPreferences userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);
                SharedPreferences.Editor userSessionEditor = userSession.edit();
                userSessionEditor.remove("sessionID");
                userSessionEditor.commit();

                User user = User.get(getApplicationContext());
                user.logout();

                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);

                break;
        }

        return false;
    }

    protected void allocateActivityTitle(String titleString){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(titleString);
        }
    }

}