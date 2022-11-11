package edu.iastate.code42;

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
import edu.iastate.code42.objects.Course;
import edu.iastate.code42.objects.User;
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

        if(user.getType().equals("student")){
            add.setVisibility(View.INVISIBLE);
        }else{
            add.setVisibility(View.VISIBLE);
        }

        courses = new ArrayList<>();

        courseAdapter = new CourseListAdapter(getApplicationContext(), courses);
        courseList.setAdapter(courseAdapter);

        getCourseList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        getCourseList();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent viewCourse = new Intent(CoursesActivity.this, CourseViewActivity.class);
        viewCourse.putExtra("courseId", courses.get(i).getId());

        startActivity(viewCourse);
    }

    @Override
    public void onClick(View view) {
        Intent creation = new Intent(CoursesActivity.this, CourseCreationActivity.class);
        startActivity(creation);
    }

    private void getCourseList(){
        courses.clear();
        String url;

        if(user.getType().equals("admin")){
            url = String.format(Const.GET_COURSES, userSession.getString("token", ""));
        }else{
            url = String.format(Const.GET_COURSES_FOR_USER, user.getId(), userSession.getString("token", ""));
        }


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
                    courseAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Login Auth Error:", error.toString());

                Toast.makeText(getApplicationContext(), url,
                        Toast.LENGTH_LONG).show();
            }
        }){
            /**
             * Passing some request headers
             **/
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

        AppController.getInstance().addToRequestQueue(courseListReq, "course_get_course");
    }

}