package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText password;
    EditText username;

    SharedPreferences userSession;
    SharedPreferences appSetting;
    SharedPreferences.Editor userSessionEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);
        appSetting = getSharedPreferences(getString(R.string.app_shared_pref), MODE_PRIVATE);

        userSessionEditor = userSession.edit();

        /*if(userSession.contains("token")){
            String url = Const.SOURCE + Const.SESSION + userSession.getString("sessionID", "");

            JsonObjectRequest loginReq = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        loginSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Login Auth Error:", error.toString());
                    if(error.networkResponse.statusCode == 401){
                        Toast.makeText(getApplicationContext(), R.string.login_volley_session,
                                Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), R.string.login_volley_error,
                                Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getApplicationContext(), R.string.login_volley_error,
                            Toast.LENGTH_LONG).show();
                }
            }){
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

            AppController.getInstance().addToRequestQueue(loginReq, "session_req");
        }*/

        login = findViewById(R.id.loginButton);
        password = findViewById(R.id.loginPasswordEntryField);
        username = findViewById(R.id.loginUsernameEntryField);

        login.setOnClickListener(this);
    }

    private void loginSuccess(JSONObject response) throws JSONException, ParseException {
        User user = User.get(getApplicationContext());
        user.fromJson(response);

        Intent dashboard = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(dashboard);
    }

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
                                //userSessionEditor.putString("token", response.getString("token"));
                                userSessionEditor.putString("token", "test");
                                userSessionEditor.commit();
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