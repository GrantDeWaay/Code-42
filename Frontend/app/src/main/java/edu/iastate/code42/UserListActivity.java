package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.Const;
import edu.iastate.code42.utils.UserListAdapter;

public class UserListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListView userList;
    FloatingActionButton add;
    Button addSelected;
    User user;
    SharedPreferences userSession;

    ArrayList<User> allUsers;
    ArrayList<User> request;
    ArrayList<User> users;
    UserListAdapter userAdapter;

    int type;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!(getIntent().hasExtra("type")) || !(userSession.contains("token"))){
            Intent dash = new Intent(this, DashboardActivity.class);
            startActivity(dash);
        }

        userList = findViewById(R.id.userList);
        userList.setOnItemClickListener(this);

        add = findViewById(R.id.addUser);
        add.setOnClickListener(this);

        addSelected = findViewById(R.id.addSelectButton);
        addSelected.setOnClickListener(this);
        addSelected.setVisibility(View.INVISIBLE);

        allUsers = new ArrayList<>();
        request = new ArrayList<>();
        users = new ArrayList<>();
        String url = "";
        type = getIntent().getIntExtra("type",-1);

        if(getIntent().hasExtra("courseId")){
            courseId = getIntent().getIntExtra("courseId", -1);
        }

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
        }


        if(type == 1 || type == 3){
            url = String.format(Const.GET_TEACHERS_FOR_COURSE, getIntent().getIntExtra("courseId",0),
                    userSession.getString("token", ""));
        }else if(type == 2 || type == 4){
            url = String.format(Const.GET_STUDENTS_FOR_COURSE, getIntent().getIntExtra("courseId",0),
                    userSession.getString("token", ""));
        }

        if(type != 0) {
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
                    }else{
                        if (request.size() > 0) {
                            userAdapter = new UserListAdapter(getApplicationContext(), request);
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
        }else{
            if (allUsers.size() > 0) {
                userAdapter = new UserListAdapter(getApplicationContext(), allUsers);
                userList.setAdapter(userAdapter);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch(type) {
            case 0://ALL
                break;

            case 1://TEACHERS
                break;

            case 2://STUDENTS
                break;

            case 3://TEACHERS FOR COUSRE
                break;

            case 4://STUDENTS FOR COUSRE
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addUser:
                break;

        }
    }
}