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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.CoursesActivity;
import edu.iastate.code42.DashboardActivity;
import edu.iastate.code42.MainActivity;
import edu.iastate.code42.R;
import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.User;

public class BaseDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    TextView headerName;
    User user;

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

        headerName = navigationView.getHeaderView(0).findViewById(R.id.headerName);

        user = User.get(getApplicationContext());
        if(user != null){
            headerName.setText(user.getFullName());
        }


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
                User user = User.get(getApplicationContext());

                String url = String.format(Const.LOGOUT, user.getUsername(),
                        userSession.getString("token", ""));

                JsonObjectRequest logoutReq = new JsonObjectRequest(Request.Method.GET, url,
                        null, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(!error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value OK of type java.lang.String cannot be converted to JSONObject")){
                            Log.e("Volley Login Auth Error:", error.toString());
                        }
                    }
                }){
                    /**
                     * Passing some request headers
                     * */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();

                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        return params;
                    }
                };

                AppController.getInstance().addToRequestQueue(logoutReq, "login_req");

                userSessionEditor.remove("sessionID");
                userSessionEditor.commit();

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