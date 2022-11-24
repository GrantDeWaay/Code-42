package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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
import com.android.volley.toolbox.StringRequest;

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

/**
 * UserCreationActivity class
 * Screen to create User
 * Layout: activity_user_creation
 * @author Andrew
 */
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

    int type;//1 for Teacher, 2 for Student
    int courseId;

    /**
     * Creates and draws the view; initializes the objects
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseBackBinding = ActivityUserCreationBinding.inflate(getLayoutInflater());
        setContentView(activityBaseBackBinding.getRoot());
        allocateActivityTitle("");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);
        appSetting = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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

        if(appSetting.contains("is_default_pass") && appSetting.contains("default_pass")){
            if(appSetting.getBoolean("is_default_pass",false)){
                password.setText(appSetting.getString("default_pass", ""));
            }
        }
    }

    /**
     * Event handler for when Create button pressed; Performs CREATE_USER HTTP Request
     * @param view Button View that is Pressed
     */
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
                jsonBody.put("firstName", firstName.getText().toString().trim());
                jsonBody.put("lastName", lastName.getText().toString().trim());
                jsonBody.put("username", username.getText().toString().trim());
                jsonBody.put("email", email.getText().toString().trim());
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
                    if(appSetting.getBoolean("is_auto_add", false)){
                        addUsertoCourse(response);
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
                    Log.e("User Creation Error", error.toString());
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

    private void addUsertoCourse(JSONObject response){
        String url = "";
        try {
             url = String.format(Const.ADD_USER_TO_COURSE, courseId, response.getInt("id"),
                    userSession.getString("token", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest addUserToCourse = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent userListIntent = new Intent(UserCreationActivity.this, UserListActivity.class);
                        userListIntent.putExtra("courseId", courseId);
                        userListIntent.putExtra("type", type - 2);

                        startActivity(userListIntent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("User to Course Mapping Error:", error.toString());
            }
        }) {
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

        AppController.getInstance().addToRequestQueue(addUserToCourse, "course_get_students");
    }

}