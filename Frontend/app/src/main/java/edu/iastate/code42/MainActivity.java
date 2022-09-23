package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import edu.iastate.code42.utils.Const;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText password;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences userSession = getSharedPreferences("user_sessions", MODE_PRIVATE);
        SharedPreferences appSetting = getSharedPreferences("app_setting", MODE_PRIVATE);

        SharedPreferences.Editor userSessionEditor = userSession.edit();

        if(userSession.contains("sessionID")){
            //Session Reauthentication code
        }

        login = findViewById(R.id.loginButton);
        password = findViewById(R.id.loginPasswordEntryField);
        username = findViewById(R.id.loginUsernameEntryField);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText() != null && !(password.getText().toString().isEmpty()) &&
                        username.getText() != null && !(username.getText().toString().isEmpty())){
                    String url = Const.SOURCE + Const.LOGIN + username.getText().toString() + "/"
                            + password.getText().toString();

                    JsonObjectRequest loginReq = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Login Auth Error:", error.toString());
                            Toast.makeText(getApplicationContext(), R.string.login_volley_error,
                                    Toast.LENGTH_LONG).show();
                        }
                    }){

                    };
                }else{
                    Toast.makeText(getApplicationContext(), R.string.login_missing_field,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}