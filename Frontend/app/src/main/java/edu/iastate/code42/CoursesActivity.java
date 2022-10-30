package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityCoursesBinding;
import edu.iastate.code42.databinding.ActivityDashboardBinding;
import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.objects.Course;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.AssignmentListAdapter;
import edu.iastate.code42.utils.BaseDrawer;
import edu.iastate.code42.utils.Const;
import edu.iastate.code42.utils.CourseListAdapter;

public class CoursesActivity extends BaseDrawer implements AdapterView.OnItemClickListener, View.OnClickListener {

    ActivityCoursesBinding activityBaseDrawerBinding;
    ListView courseList;
    FloatingActionButton add;
    User user;
    SharedPreferences userSession;

    ArrayList<Course> courses;
    CourseListAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityCoursesBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("Courses");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!userSession.contains("token")){
            Intent login = new Intent(CoursesActivity.this, MainActivity.class);
            startActivity(login);
        }

        courseList = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseList);
        courseList.setOnItemClickListener(this);

        add = activityBaseDrawerBinding.getRoot().findViewById(R.id.addCourse);
        add.setOnClickListener(this);

        String url = String.format(Const.GET_COURSES_FOR_USER, user.getId());

        JsonArrayRequest courseListReq = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        Course c = new Course(response.getJSONObject(i));
                        courses.add(c);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    courseAdapter = new CourseListAdapter(getApplicationContext(), courses);
                    courseList.setAdapter(courseAdapter);
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", userSession.getString("token", ""));

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(courseListReq, "course_get_course");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onClick(View view) {
        Intent creation = new Intent(CoursesActivity.this, CourseCreationActivity.class);
        startActivity(creation);
    }
}