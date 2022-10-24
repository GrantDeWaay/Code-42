package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityCourseViewBinding;
import edu.iastate.code42.databinding.ActivityCoursesBinding;
import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseDrawer;
import edu.iastate.code42.utils.Const;

public class CourseViewActivity extends BaseDrawer implements View.OnClickListener {

    ActivityCourseViewBinding activityBaseDrawerBinding;
    EditText title;
    EditText description;
    EditText language;
    FloatingActionButton edit;

    ListView assignmentList;
    Button addAssignment;
    Button moreAssigment;

    TableLayout studentLayout;
    ListView studentList;
    Button addStudent;
    Button moreStudent;

    User user;

    boolean viewState;
    int courseId;

    ArrayList<Assignment> assignments;
    ArrayList<User> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityCourseViewBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("");

        user = User.get(getApplicationContext());

        title = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseTitleHeader);
        description = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseDescriptionView);
        language = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseLanguagesView);
        edit = activityBaseDrawerBinding.getRoot().findViewById(R.id.floatingEditCourse);

        assignmentList = activityBaseDrawerBinding.getRoot().findViewById(R.id.assignmentList);
        addAssignment = activityBaseDrawerBinding.getRoot().findViewById(R.id.addAssignmentButton);
        moreAssigment = activityBaseDrawerBinding.getRoot().findViewById(R.id.moreAssignmentButton);

        studentLayout = activityBaseDrawerBinding.getRoot().findViewById(R.id.studentLayout);
        studentList = activityBaseDrawerBinding.getRoot().findViewById(R.id.studentList);
        addStudent = activityBaseDrawerBinding.getRoot().findViewById(R.id.addStudentButton);
        moreStudent = activityBaseDrawerBinding.getRoot().findViewById(R.id.moreStudentButton);


        edit.setOnClickListener(this);
        addAssignment.setOnClickListener(this);
        moreAssigment.setOnClickListener(this);

        addStudent.setOnClickListener(this);
        moreStudent.setOnClickListener(this);

        if(user.getType().equals("student")){
            edit.setVisibility(View.INVISIBLE);
            edit.setEnabled(false);
            studentLayout.setVisibility(View.INVISIBLE);
            addAssignment.setVisibility(View.INVISIBLE);
        }else{
            edit.setVisibility(View.VISIBLE);
            edit.setEnabled(true);
            studentLayout.setVisibility(View.VISIBLE);
            addAssignment.setVisibility(View.VISIBLE);
        }


        if(getIntent().hasExtra("courseId")){
            courseId = getIntent().getIntExtra("courseId",0);

            title.setEnabled(false);
            description.setEnabled(false);
            language.setEnabled(false);
            viewState = true;

            assignments = new ArrayList<>();
            students = new ArrayList<>();

            String url = String.format(Const.GET_COURSE,
                    courseId);

            JsonObjectRequest courseDetailReq = new JsonObjectRequest(Request.Method.GET, url,
                    null,  new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        title.setText(response.getString("title"));
                        description.setText(response.getString("description"));
                        language.setText(response.getString("languages"));
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

            AppController.getInstance().addToRequestQueue(courseDetailReq, "course_get_req");

            url = String.format(Const.GET_ASSIGNMENTS_FOR_COURSE, courseId);

            JsonArrayRequest courseAssignmentsReq = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i = 0; i < response.length(); i++){
                        try {
                            Assignment a = new Assignment(response.getJSONObject(i));
                            assignments.add(a);
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

                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(courseAssignmentsReq, "course_get_assignments");

            if(user.getType() != "student") {
                url = String.format(Const.GET_USERS_FOR_COURSE, courseId);

                JsonArrayRequest courseStudentsReq = new JsonArrayRequest(Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                User u = new User(response.getJSONObject(i));

                                if(u.getType().equals("student")){
                                    students.add(u);
                                }
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

                AppController.getInstance().addToRequestQueue(courseStudentsReq, "course_get_students");
            }
        }else{
            Intent courseList = new Intent(this, CoursesActivity.class);
            startActivity(courseList);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.floatingEditCourse:
                break;

            case R.id.addAssignmentButton:
                break;

            case R.id.moreAssignmentButton:
                break;

            case R.id.addStudentButton:
                break;

            case R.id.moreStudentButton:
                break;
        }
    }
}