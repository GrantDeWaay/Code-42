package edu.iastate.code42;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityUserViewBinding;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseBack;
import edu.iastate.code42.utils.Const;

/**
 * Screen to view and edit details about individual User
 * viewType 0: View and edit current User
 * viewType 1: View and edit different User (Admin and Teacher Users only)
 * Layout: activity_user_view
 * Extends BaseDrawer
 * @author Andrew
 */
public class UserViewActivity extends BaseBack implements View.OnClickListener {
    ActivityUserViewBinding activityBaseDrawerBinding;

    EditText firstName;
    EditText lastName;
    EditText username;
    EditText email;

    FloatingActionButton edit;
    Button delete;
    Button remove;
    Button changePass;

    User user;
    User viewUser;
    SharedPreferences userSession;

    boolean viewState;
    boolean changePassword;

    int viewType;
    int userId;
    int courseId;

    /**
     * Creates and draws the view; initializes the objects
     * Performs GET_USER HTTP Request
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityUserViewBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!userSession.contains("token")){
            Intent login = new Intent(UserViewActivity.this, MainActivity.class);
            startActivity(login);
        }

        //TODO Initialize view objects
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        remove.setOnClickListener(this);
        changePass.setOnClickListener(this);

        if(getIntent().hasExtra("courseId") && getIntent().hasExtra("type") && getIntent().hasExtra("userId")){
            viewType = 1;

            userId = getIntent().getIntExtra("userId", -1);
            courseId = getIntent().getIntExtra("courseId", -1);

            Intent userList = new Intent(UserViewActivity.this, UserListActivity.class);
            userList.putExtra("courseId", getIntent().getIntExtra("courseId", -1));
            userList.putExtra("type", getIntent().getIntExtra("type", -1));
            setPreviousScreen(userList);
        }else{
            viewType = 0;
            viewUser = null;

            userId = user.getId();

            setPreviousScreen(new Intent(UserViewActivity.this, DashboardActivity.class));
        }

        getUserDetails();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case 1://Edit
                if(viewState){
                    viewState = false;
                }else{
                    updateUserDetails();

                    viewState = true;
                }

                updateViewState(viewState);

                break;

            case 2://Delete
                deleteUser();
                break;

            case 3://Remove
                removeFromCourse();
                break;

            case 4://Change Pass
                break;
        }

    }

    private void updateViewState(Boolean state){//TODO Add object visibility logic
        if(state){
            setSave(false);

            edit.setImageDrawable(getDrawable(R.drawable.ic_edit_foreground));
        }else{
            setSave(true);

            edit.setImageDrawable(getDrawable(R.drawable.ic_save_foreground));
        }
    }

    private void getUserDetails(){
        String url = String.format(Const.GET_USER, userId, userSession.getString("token", ""));

        JsonObjectRequest userDetailReq = new JsonObjectRequest(Request.Method.GET, url,
                null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //TODO Set content to view objects
                    if(viewType == 0){
                        user.fromJson(response);
                    }else{
                        viewUser =  new User(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

        AppController.getInstance().addToRequestQueue(userDetailReq, "user_get_req");
    }

    private void updateUserDetails(){
        String url = String.format(Const.UPDATE_USER,userId, userSession.getString("token", ""));
        JSONObject jsonBody = null;

        try {
            jsonBody = update();

            if (viewType == 0) {
                user.fromJson(jsonBody);
            } else {
                viewUser.fromJson(jsonBody);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest updateReq = new JsonObjectRequest(Request.Method.PUT, url,
                jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value ACCEPTED of type java.lang.String cannot be converted to JSONObject")) {
                    Log.e("Volley Course Update Error", error.toString());
                    viewState = false;
                    updateViewState(viewState);

                    Toast.makeText(getApplicationContext(), "Error saving changes, try again",
                            Toast.LENGTH_LONG).show();
                }
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

        AppController.getInstance().addToRequestQueue(updateReq, "update_user_req");
    }

    private JSONObject update() throws JSONException {
        User u = new User();

        if(changePassword){
            if(viewType == 0){
                //TODO Update password if current matches user.getPassword &&
                //     new pass matches confirm box
            }else{
                //TODO Update password if new pass matches confirm box
            }
        }
        //TODO Set variables to values of text boxes

        return u.getJson();
    }


    private void deleteUser(){
        String url = String.format(Const.DELETE_USER, userId,
                userSession.getString("token", ""));

        StringRequest deleteReq = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent courseView = new Intent(UserViewActivity.this, CourseViewActivity.class);
                        courseView.putExtra("courseId", courseId);

                        startActivity(courseView);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Delete Course Error", error.toString());

                Toast.makeText(getApplicationContext(), "Error saving changes, try again later",
                        Toast.LENGTH_LONG).show();
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

        AppController.getInstance().addToRequestQueue(deleteReq, "delete_user_req");

    }

    private void removeFromCourse(){
        String url = String.format(Const.USER_TO_COURSE, courseId, userId,
                userSession.getString("token", ""));

        StringRequest removeUserFromCourse = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent courseView = new Intent(UserViewActivity.this, CourseViewActivity.class);
                        courseView.putExtra("courseId", courseId);

                        startActivity(courseView);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("User to Course Mapping Error:", error.toString());

                Toast.makeText(getApplicationContext(), R.string.user_course_mapping_error,
                        Toast.LENGTH_LONG).show();
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

        AppController.getInstance().addToRequestQueue(removeUserFromCourse, "course_get_students");
    }
}