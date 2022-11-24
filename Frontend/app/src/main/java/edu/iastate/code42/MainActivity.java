package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.Const;

/**
 * MainActivity class
 * First activity of application, login screen
 * Layout: activity_main
 * @author Andrew
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText password;
    EditText username;

    SharedPreferences userSession;
    SharedPreferences appSetting;
    SharedPreferences.Editor userSessionEditor;
    SharedPreferences.Editor settingEditor;

    /**
     * Creates and draws the view; initializes the objects
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);
        appSetting = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        userSessionEditor = userSession.edit();
        settingEditor = appSetting.edit();

        settingEditor.putBoolean("admin", false);
        settingEditor.commit();

        if(appSetting.contains("theme")){
            if(appSetting.getString("theme", "").equals(getString(R.string.theme_options_1))){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else if(appSetting.getString("theme", "").trim().equals(getString(R.string.theme_options_2))){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        } else{
            settingEditor.putString("theme", getString(R.string.theme_options_0));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }

        if(userSession.contains("token")) {
            String url = String.format(Const.SESSION, userSession.getString("token",""));

            JsonObjectRequest loginReq = new JsonObjectRequest(Request.Method.GET, url,
                    null,  new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        response.put("token", userSession.getString("token",""));
                        loginSuccess(response);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Token Auth Error:", error.toString());

                    Toast.makeText(getApplicationContext(), "Token expired, login with username and password",
                            Toast.LENGTH_LONG).show();
                }
            }){
                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(loginReq, "login_req");
        }

        login = findViewById(R.id.loginButton);
        password = findViewById(R.id.loginPasswordEntryField);
        username = findViewById(R.id.loginUsernameEntryField);

        login.setOnClickListener(this);
    }

    /**
     * Helper method for when login is successful; Stores user data locally and goes to Dashboard
     * @param response
     * @throws JSONException
     * @throws ParseException
     */
    private void loginSuccess(JSONObject response) throws JSONException, ParseException {
        User user = User.get(getApplicationContext());
        user.fromJson(response);

        if(user.getType().equals("student")){
            settingEditor.putBoolean("admin", false);
        }else{
            settingEditor.putBoolean("admin", true);
        }
        settingEditor.commit();

        userSessionEditor.putString("token", response.getString("token"));
        userSessionEditor.commit();

        Intent dashboard = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(dashboard);
    }

    /**
     * Event handler for when Login button pressed; Performs LOGIN HTTP Request
     * @param view Button View that is Pressed
     */
    @Override
    public void onClick(View view) {
                if(password.getText() != null && !(password.getText().toString().isEmpty()) &&
                        username.getText() != null && !(username.getText().toString().isEmpty())){
                    String url = String.format(Const.LOGIN, username.getText().toString(),
                            password.getText().toString());

                    JsonObjectRequest loginReq = new JsonObjectRequest(Request.Method.GET, url,
                            null,  new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            /*Toast.makeText(getApplicationContext(), response.toString(),
                                    Toast.LENGTH_LONG).show();*/
                            try {
                                loginSuccess(response);
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Login Auth Error:", error.toString());

                            Toast.makeText(getApplicationContext(), R.string.login_volley_error,
                                    Toast.LENGTH_LONG).show();
                        }
                    }){
                        /**
                         * Passing some request headers
                         * */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            return params;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(loginReq, "login_req");
                }else{
                    Toast.makeText(getApplicationContext(), R.string.login_missing_field,
                            Toast.LENGTH_LONG).show();
                }
    }
}