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
import edu.iastate.code42.databinding.ActivityCourseCreationBinding;
import edu.iastate.code42.databinding.ActivityUserCreationBinding;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseBack;
import edu.iastate.code42.utils.Const;


public class UserCreationActivity extends BaseBack implements View.OnClickListener {
    ActivityUserCreationBinding activityBaseBackBinding;

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

        activityBaseBackBinding = ActivityUserCreationBinding.inflate(getLayoutInflater());
        setContentView(activityBaseBackBinding.getRoot());
        allocateActivityTitle("");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);
        appSetting = getSharedPreferences(getString(R.string.app_shared_pref), MODE_PRIVATE);

        if(!(getIntent().hasExtra("type")) || !(userSession.contains("token"))){
            Intent dash = new Intent(this, DashboardActivity.class);
            startActivity(dash);
        }
        type = getIntent().getIntExtra("type", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        header = activityBaseBackBinding.getRoot().findViewById(R.id.userCreationHeader);
        if(type == 1){
            header.setText("Create new Teacher user");
        }else if(type == 2){
            header.setText("Create new Student user");
        }

        create = activityBaseBackBinding.getRoot().findViewById(R.id.buttonUserCreate);
        create.setOnClickListener(this);

        firstName = activityBaseBackBinding.getRoot().findViewById(R.id.editUserFirstName);
        lastName = activityBaseBackBinding.getRoot().findViewById(R.id.editUserLastName);
        email = activityBaseBackBinding.getRoot().findViewById(R.id.editUserEmail);
        password = activityBaseBackBinding.getRoot().findViewById(R.id.editUserPassword);
        username = activityBaseBackBinding.getRoot().findViewById(R.id.editUserUsername);

        Intent userListReturn = new Intent(UserCreationActivity.this, UserListActivity.class);
        userListReturn.putExtra("courseId", courseId);
        userListReturn.putExtra("type", type);

        setPreviousScreen(userListReturn);
        setSave(true);

        if(appSetting.contains("isDefaultPassword") && appSetting.contains("defaultPassword")){//TODO Test in Demo 4 with Settings activity
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
            String url = String.format(Const.CREATE_USER, userSession.getString("token", ""));
            JSONObject jsonBody = new JSONObject();

            try {
                jsonBody.put("firstName", firstName.getText());
                jsonBody.put("lastName", lastName.getText());
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
                        //TODO in Demo 4 with Settings activity
                    }else{
                        Intent userListReturn = new Intent(UserCreationActivity.this, UserListActivity.class);
                        try {
                            userListReturn.putExtra("userId", response.getInt("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        userListReturn.putExtra("courseId", courseId);
                        userListReturn.putExtra("type", type);

                        startActivity(userListReturn);
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