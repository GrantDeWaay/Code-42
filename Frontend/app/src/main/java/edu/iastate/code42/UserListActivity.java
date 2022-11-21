package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityCourseCreationBinding;
import edu.iastate.code42.databinding.ActivityUserListBinding;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseBack;
import edu.iastate.code42.utils.Const;
import edu.iastate.code42.utils.UserListAdapter;

/**
 * UserListActivity class
 * View with a list of Users, multiple configurations and uses based on User type and Intent type
 * Layout: activity_user_list
 * @author Andrew
 */
public class UserListActivity extends BaseBack implements AdapterView.OnItemClickListener, View.OnClickListener {
    ActivityUserListBinding activityBaseBackBinding;

    ListView userList;
    FloatingActionButton add;
    Button addSelected;
    User user;
    SharedPreferences userSession;

    ArrayList<User> allUsers;
    ArrayList<User> request;
    ArrayList<User> selected;
    ArrayList<User> users;
    UserListAdapter userAdapter;

    int i;
    int type;
    int courseId;
    String url;

    /**
     * Creates and draws the view; initializes the objects
     * Performs GET_USERS, GET_TEACHERS_FOR_COURSE, and GET_STUDENTS_FOR_COURSE HTTP Requests
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseBackBinding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(activityBaseBackBinding.getRoot());
        allocateActivityTitle("");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!(getIntent().hasExtra("type")) || !(userSession.contains("token"))){
            Intent dash = new Intent(this, DashboardActivity.class);
            startActivity(dash);
        }
        type = getIntent().getIntExtra("type",-1);

        if(getIntent().hasExtra("courseId")){
            courseId = getIntent().getIntExtra("courseId", -1);
        }


        userList = activityBaseBackBinding.getRoot().findViewById(R.id.userList);
        userList.setOnItemClickListener(this);
        if(type == 1 || type == 2){
            userList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        }
        userList.setItemsCanFocus(true);

        add = activityBaseBackBinding.getRoot().findViewById(R.id.addUser);
        add.setOnClickListener(this);

        addSelected = activityBaseBackBinding.getRoot().findViewById(R.id.addSelectButton);
        addSelected.setOnClickListener(this);
        addSelected.setVisibility(View.INVISIBLE);
        addSelected.setEnabled(false);

        allUsers = new ArrayList<>();
        request = new ArrayList<>();
        selected = new ArrayList<>();
        users = new ArrayList<>();


        if(type == 1 || type == 2){
            Intent userListIntent = new Intent(this, UserListActivity.class);
            userListIntent.putExtra("courseId", courseId);
            userListIntent.putExtra("type", type + 2);

            setPreviousScreen(userListIntent);
            setSave(true);
        }else if(type == 3 || type == 4){
            Intent courseView = new Intent(this, CourseViewActivity.class);
            courseView.putExtra("courseId", courseId);

            setPreviousScreen(courseView);
            setSave(false);
        }

        getUsers();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        getUsers();
    }

    /**
     * Event Handler for when item in the ListView selected
     * @param adapterView AdaperView for ListView
     * @param view ListView of item selected
     * @param i Position of item selected
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch(type) {
            case 1://TEACHERS not in COURSE
            case 2://STUDENTS not in COURSE
                User u = userAdapter.getItem(i);

                if(selected.contains(u)){
                    userAdapter.getView(i, view, userList).setBackgroundColor(getColor(R.color.white));
                    selected.remove(u);
                }else{
                    userAdapter.getView(i, view, userList).setBackgroundColor(getColor(R.color.light_gray));
                    selected.add(u);
                }

                if(selected.size() > 0){
                    addSelected.setVisibility(View.VISIBLE);
                    addSelected.setEnabled(true);
                }else{
                    addSelected.setVisibility(View.INVISIBLE);
                    addSelected.setEnabled(false);
                }

                break;

            case 3://TEACHERS in COURSE
                //TODO when UserProfile/UserEdit complete
                break;

            case 4://STUDENTS in COURSE
                //TODO when UserProfile/UserEdit complete
                break;
        }
    }

    /**
     * Event handler for when any button is pressed; Opens various activities depending on button
     * @param view Button View that is Pressed
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addUser:
                if(type == 1 || type == 2){
                    Intent userCreationIntent = new Intent(this, UserCreationActivity.class);
                    userCreationIntent.putExtra("courseId", courseId);
                    userCreationIntent.putExtra("type", type);

                    startActivity(userCreationIntent);
                    finish();
                }else if(type == 3 || type == 4){
                    Intent userListIntent = new Intent(this, UserListActivity.class);
                    userListIntent.putExtra("courseId", courseId);
                    userListIntent.putExtra("type", type - 2);

                    startActivity(userListIntent);
                    finish();
                }
                break;

            case R.id.addSelectButton:
                for(i = 0; i < selected.size(); i++){
                    url = String.format(Const.ADD_USER_TO_COURSE, courseId, selected.get(i).getId(),
                            userSession.getString("token", ""));

                    StringRequest addUserToCourse = new StringRequest(Request.Method.PUT, url,
                            new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(i == selected.size()){
                                Intent userListAdded = new Intent(UserListActivity.this, UserListActivity.class);
                                userListAdded.putExtra("type", type + 2);
                                userListAdded.putExtra("courseId", courseId);

                                startActivity(userListAdded);
                            }
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

                    AppController.getInstance().addToRequestQueue(addUserToCourse, "course_get_students");
                }

                break;
        }
    }

    private void getUsers(){
        url = "";
        allUsers.clear();
        request.clear();
        selected.clear();
        users.clear();

        if(type == 0 || type == 1 || type == 2){
            url = String.format(Const.GET_USERS, userSession.getString("token", ""));

            JsonArrayRequest userListAll = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i = 0; i < response.length(); i++){
                        try {
                            User u = new User(response.getJSONObject(i));

                            allUsers.add(u);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if(type == 0){
                        if (allUsers.size() > 0) {
                            userAdapter = new UserListAdapter(getApplicationContext(), allUsers);
                            userList.setAdapter(userAdapter);
                        }
                    }else{
                        if(type == 1){
                            url = String.format(Const.GET_TEACHERS_FOR_COURSE, getIntent().getIntExtra("courseId",0),
                                    userSession.getString("token", ""));
                        }else if(type == 2){
                            url = String.format(Const.GET_STUDENTS_FOR_COURSE, getIntent().getIntExtra("courseId",0),
                                    userSession.getString("token", ""));
                        }

                        JsonArrayRequest userListSpecific = new JsonArrayRequest(Request.Method.GET, url,
                                null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        User u = new User(response.getJSONObject(i));

                                        request.add(u);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(type == 1){
                                    for(int i = 0; i < allUsers.size(); i++){
                                        if(!(request.contains(allUsers.get(i))) && allUsers.get(i).getType().equals("teacher")){
                                            users.add(allUsers.get(i));
                                        }
                                    }

                                    if (users.size() > 0) {
                                        userAdapter = new UserListAdapter(getApplicationContext(), users);
                                        userList.setAdapter(userAdapter);
                                    }
                                }else if(type == 2){
                                    for(int i = 0; i < allUsers.size(); i++){
                                        if(!(request.contains(allUsers.get(i))) && allUsers.get(i).getType().equals("student")){
                                            users.add(allUsers.get(i));
                                        }
                                    }

                                    if (users.size() > 0) {
                                        userAdapter = new UserListAdapter(getApplicationContext(), users);
                                        userList.setAdapter(userAdapter);
                                    }
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
                        AppController.getInstance().addToRequestQueue(userListSpecific, "course_get_students");
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

            AppController.getInstance().addToRequestQueue(userListAll, "user_list_all");
        } else if(type == 3 || type == 4) {
            if(type == 3){
                url = String.format(Const.GET_TEACHERS_FOR_COURSE, getIntent().getIntExtra("courseId",0),
                        userSession.getString("token", ""));
            }else if(type == 4){
                url = String.format(Const.GET_STUDENTS_FOR_COURSE, getIntent().getIntExtra("courseId",0),
                        userSession.getString("token", ""));
            }

            JsonArrayRequest userListSpecific = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            User u = new User(response.getJSONObject(i));

                            request.add(u);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (request.size() > 0) {
                        userAdapter = new UserListAdapter(getApplicationContext(), request);
                        userList.setAdapter(userAdapter);
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
            AppController.getInstance().addToRequestQueue(userListSpecific, "course_get_students");
        }
    }
}