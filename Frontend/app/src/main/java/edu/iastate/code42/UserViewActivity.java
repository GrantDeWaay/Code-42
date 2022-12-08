package edu.iastate.code42;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

    EditText currentPass;
    EditText newPass;
    EditText confirmPass;

    FloatingActionButton edit;
    Button delete;
    Button remove;
    Button changePass;
    LinearLayout passView;

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

        firstName = activityBaseDrawerBinding.getRoot().findViewById(R.id.firstNameView);
        lastName = activityBaseDrawerBinding.getRoot().findViewById(R.id.lastNameView);
        username = activityBaseDrawerBinding.getRoot().findViewById(R.id.usernameView);
        email = activityBaseDrawerBinding.getRoot().findViewById(R.id.emailView);

        currentPass = activityBaseDrawerBinding.getRoot().findViewById(R.id.currentPassword);
        newPass = activityBaseDrawerBinding.getRoot().findViewById(R.id.newPassword);
        confirmPass = activityBaseDrawerBinding.getRoot().findViewById(R.id.confirmPassword);

        edit = activityBaseDrawerBinding.getRoot().findViewById(R.id.floatingEditUser);
        delete = activityBaseDrawerBinding.getRoot().findViewById(R.id.deleteUserButton);
        remove = activityBaseDrawerBinding.getRoot().findViewById(R.id.removeMappingButton);
        changePass = activityBaseDrawerBinding.getRoot().findViewById(R.id.changePasswordButton);
        passView = activityBaseDrawerBinding.getRoot().findViewById(R.id.passwordContainer);

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
        viewState = true;
        updateViewState();

        getUserDetails();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        viewState = true;
        updateViewState();

        getUserDetails();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floatingEditUser://Edit
                if(viewState){
                    viewState = false;
                }else{
                    updateUserDetails();

                    viewState = true;
                }

                updateViewState();

                break;

            case R.id.deleteUserButton://Delete
                deleteUser();
                break;

            case R.id.removeMappingButton://Remove
                removeFromCourse();
                break;

            case R.id.changePasswordButton://Change Pass
                changePassword = true;

                updatePasswordView();
                break;
        }

    }

    private void updateViewState(){
        if(viewState){
            setSave(false);

            firstName.setEnabled(false);
            lastName.setEnabled(false);
            username.setEnabled(false);
            email.setEnabled(false);

            delete.setVisibility(View.INVISIBLE);
            remove.setVisibility(View.INVISIBLE);

            updatePasswordView();

            edit.setImageDrawable(getDrawable(R.drawable.ic_edit_foreground));
        }else{
            setSave(true);

            firstName.setEnabled(true);
            lastName.setEnabled(true);
            username.setEnabled(true);
            email.setEnabled(true);

            if(viewType == 0) {
                delete.setVisibility(View.INVISIBLE);
                remove.setVisibility(View.INVISIBLE);
            }else{
                delete.setVisibility(View.VISIBLE);
                remove.setVisibility(View.VISIBLE);
            }

            updatePasswordView();

            edit.setImageDrawable(getDrawable(R.drawable.ic_save_foreground));
        }
    }

    private void updatePasswordView(){
        if(viewState){
            changePass.setVisibility(View.INVISIBLE);

            passView.setVisibility(View.INVISIBLE);

            currentPass.setText("");
            newPass.setText("");
            confirmPass.setText("");
            changePassword = false;
        }else{
            if(changePassword){
                changePass.setVisibility(View.INVISIBLE);

                passView.setVisibility(View.VISIBLE);

                if(viewType == 0){
                    currentPass.setVisibility(View.VISIBLE);
                    newPass.setVisibility(View.VISIBLE);
                    confirmPass.setVisibility(View.VISIBLE);
                }else{
                    currentPass.setVisibility(View.INVISIBLE);
                    newPass.setVisibility(View.VISIBLE);
                    confirmPass.setVisibility(View.VISIBLE);
                }
            }else{
                changePass.setVisibility(View.VISIBLE);

                passView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void getUserDetails(){
        String url = String.format(Const.GET_USER, userId, userSession.getString("token", ""));

        JsonObjectRequest userDetailReq = new JsonObjectRequest(Request.Method.GET, url,
                null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    firstName.setText(response.getString("firstName"));
                    lastName.setText(response.getString("lastName"));
                    username.setText(response.getString("username"));
                    email.setText(response.getString("email"));

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
        String url = String.format(Const.UPDATE_USER, userId, userSession.getString("token", ""));
        JSONObject jsonBody = null;

        try {
            jsonBody = update();

            if(jsonBody != null) {
                if (viewType == 0) {
                    user.fromJson(jsonBody);
                } else {
                    viewUser.fromJson(jsonBody);
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
                            updateViewState();

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
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private JSONObject update() throws JSONException {
        JSONObject object = new JSONObject();

        object.put("id", userId);

        if(changePassword){
            if(viewType == 0){
                if(!currentPass.getText().toString().isEmpty() &&
                        !newPass.getText().toString().isEmpty() &&
                        !confirmPass.getText().toString().isEmpty() &&
                        currentPass.getText().toString().equals(user.getPassword())){
                    if(newPass.getText().toString().equals(confirmPass.getText().toString())) {
                        object.put("password", newPass.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(), "New Password doesn't match confirmation, try again",
                                Toast.LENGTH_LONG).show();
                        return null;
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Current Password does not match, try again",
                            Toast.LENGTH_LONG).show();
                    return null;
                }
            }else{
                if(!newPass.getText().toString().isEmpty() &&
                        !confirmPass.getText().toString().isEmpty() &&
                        newPass.getText().toString().equals(confirmPass.getText().toString())){
                    object.put("password", newPass.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "New Password doesn't match confirmation, try again",
                            Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        }
        object.put("firstName", firstName.getText().toString());
        object.put("lastName", lastName.getText().toString());
        object.put("username", username.getText().toString());
        object.put("email", email.getText().toString());

        return object;
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

        AppController.getInstance().addToRequestQueue(removeUserFromCourse, "user_to_course_mapping");
    }
}