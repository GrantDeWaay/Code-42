package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.Const;


public class UserCreationActivity extends AppCompatActivity implements View.OnClickListener {
    Button create;
    TextView header;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText username;

    User user;
    SharedPreferences userSession;
    SharedPreferences appSetting;

    int type;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);
        appSetting = getSharedPreferences(getString(R.string.app_shared_pref), MODE_PRIVATE);

        if(!(getIntent().hasExtra("type")) || !(userSession.contains("token"))){
            Intent dash = new Intent(this, DashboardActivity.class);
            startActivity(dash);
        }
        type = getIntent().getIntExtra("type", -1);

        header = findViewById(R.id.userCreationHeader);
        if(type == 1){
            header.setText("Create new Teacher user");
        }else if(type == 2){
            header.setText("Create new Student user");
        }

        create = findViewById(R.id.buttonUserCreate);
        create.setOnClickListener(this);

        firstName = findViewById(R.id.editUserFirstName);
        lastName = findViewById(R.id.editUserLastName);
        email = findViewById(R.id.editUserEmail);
        password = findViewById(R.id.editUserPassword);
        username = findViewById(R.id.editUserUsername);

        if(appSetting.contains("isDefaultPassword") && appSetting.contains("defaultPassword")){
            if(appSetting.getBoolean("isDefaultPassword",false)){
                password.setText(appSetting.getString("defaultPassword", ""));
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (firstName.getText() != null && !(firstName.getText().toString().isEmpty()) &&
                lastName.getText() != null && !(lastName.getText().toString().isEmpty()) &&
                email.getText() != null && !(email.getText().toString().isEmpty()) &&
                password.getText() != null && !(password.getText().toString().isEmpty()) &&
                username.getText() != null && !(username.getText().toString().isEmpty())) {
            String url = Const.CREATE_USER;
            JSONObject jsonBody = new JSONObject();

            try {
                jsonBody.put("firstName", firstName.getText());
                jsonBody.put("lastname", lastName.getText());
                jsonBody.put("username", username.getText());
                jsonBody.put("email", email.getText());
                jsonBody.put("password", password.getText());

                if(type == 1){
                    jsonBody.put("type", "teacher");
                }else if(type == 2){
                    jsonBody.put("type", "student");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest createReq = new JsonObjectRequest(Request.Method.POST, url,
                    jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(), response.toString(),
                            Toast.LENGTH_LONG).show();
                    
                    if(appSetting.getBoolean("isAutoAddUserToCourse", false)){

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Login Auth Error:", error.toString());

                    Toast.makeText(getApplicationContext(), R.string.login_volley_error,
                            Toast.LENGTH_LONG).show();
                }
            }) {
                /**
                 * Passing some request headers
                 */
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

            AppController.getInstance().addToRequestQueue(createReq, "create_user_req");
        }
    }
}