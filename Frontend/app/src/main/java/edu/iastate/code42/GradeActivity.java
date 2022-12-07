package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityCoursesBinding;
import edu.iastate.code42.databinding.ActivityGradeBinding;
import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.objects.Course;
import edu.iastate.code42.objects.Grade;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.AssignmentSpinnerAdapter;
import edu.iastate.code42.utils.BaseDrawer;
import edu.iastate.code42.utils.Const;
import edu.iastate.code42.utils.CourseListAdapter;
import edu.iastate.code42.utils.CourseSpinnerAdapter;
import edu.iastate.code42.utils.GradeListAdapter;
import edu.iastate.code42.utils.GradeTeacherListAdapter;

public class GradeActivity extends BaseDrawer implements AdapterView.OnItemSelectedListener {
    ActivityGradeBinding activityBaseDrawerBinding;

    Spinner courseSelect;
    Spinner assignmentSelect;
    ListView gradeList;

    User user;
    SharedPreferences userSession;

    ArrayList<Grade> grades;
    ArrayList<Grade> userGrades;
    GradeListAdapter gradeAdapter;
    GradeTeacherListAdapter gradeTeacherAdapter;

    ArrayList<Course> courses;
    CourseSpinnerAdapter courseAdapter;

    ArrayList<Assignment> assignments;
    AssignmentSpinnerAdapter assignmentAdapter;

    String url;
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBaseDrawerBinding = ActivityGradeBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("Grades");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if (!userSession.contains("token")) {
            Intent login = new Intent(GradeActivity.this, MainActivity.class);
            startActivity(login);
        }
        assignments = new ArrayList<>();
        courses = new ArrayList<>();
        grades = new ArrayList<>();
        userGrades = new ArrayList<>();

        courseSelect = activityBaseDrawerBinding.getRoot().findViewById(R.id.gradeCourseSelect);
        assignmentSelect = activityBaseDrawerBinding.getRoot().findViewById(R.id.gradeAssignmentSelect);
        gradeList = activityBaseDrawerBinding.getRoot().findViewById(R.id.gradeList);

        courseAdapter = new CourseSpinnerAdapter(getApplicationContext(), courses);
        courseSelect.setAdapter(courseAdapter);
        courseSelect.setOnItemSelectedListener(this);
        assignmentSelect.setOnItemSelectedListener(this);

        gradeAdapter = new GradeListAdapter(getApplicationContext(), grades);
        gradeList.setAdapter(gradeAdapter);

        if (user.getType().equals("student")) {
            assignmentSelect.setVisibility(View.INVISIBLE);

            url = String.format(Const.GET_GRADES_FOR_USER, user.getId(), userSession.getString("token", ""));

            JsonArrayRequest courseListReq = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("User Grades", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            Grade g = new Grade(response.getJSONObject(i).getInt("id"), user,
                                    response.getJSONObject(i).getDouble("grade"), 100.0);

                            userGrades.add(g);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Login Auth Error:", error.toString());

                    Toast.makeText(getApplicationContext(), url,
                            Toast.LENGTH_LONG).show();
                }
            }) {
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

        } else {
            assignmentSelect.setVisibility(View.VISIBLE);
            assignmentSelect.setEnabled(false);
        }

        url = String.format(Const.GET_COURSES_FOR_USER, user.getId(), userSession.getString("token", ""));

        JsonArrayRequest courseListReq = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
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
        }) {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("Test", "test");

        int courseId = courses.get(i).getId();
        assignments.clear();
        grades.clear();

        Log.i("Course Number: ", Integer.toString(courseId));

        if (user.getType().equals("student")) {
            url = String.format(Const.GET_ASSIGNMENTS_FOR_COURSE, courseId, userSession.getString("token", ""));

            JsonArrayRequest courseAssignmentsReq = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int k = 0; k < response.length(); k++) {
                        try {
                            Assignment a = new Assignment(response.getJSONObject(k));
                            Log.i("Assignment: ", a.toString());

                            assignments.add(a);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    for (j = 0; j < assignments.size(); j++) {
                        url = String.format(Const.GET_GRADES_FOR_ASSIGNMENT, assignments.get(j).getId(),
                                userSession.getString("token", ""));

                        JsonArrayRequest assignmentGradeReq = new JsonArrayRequest(Request.Method.GET, url,
                                null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.i("Grades for Assignments", response.toString());
                                for (int k = 0; k < response.length(); k++) {
                                    try {
                                        Grade g = new Grade(response.getJSONObject(k).getInt("id"),
                                                assignments.get(j), response.getJSONObject(k).getDouble("grade"),
                                                100.0);

                                        if (userGrades.contains(g)) {
                                            Grade grade = new Grade(g.getId(), assignments.get(j), user,
                                                    g.getGrade(), g.getGradeTotal());
                                            grades.add(grade);
                                            gradeAdapter.notifyDataSetChanged();
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

                                Toast.makeText(getApplicationContext(), url,
                                        Toast.LENGTH_LONG).show();
                            }
                        }) {
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

                        AppController.getInstance().addToRequestQueue(assignmentGradeReq, "course_get_course");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Login Auth Error:", error.toString());

                    Toast.makeText(getApplicationContext(), url,
                            Toast.LENGTH_LONG).show();
                }
            }) {
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

            AppController.getInstance().addToRequestQueue(courseAssignmentsReq, "course_get_course");
        } else if (user.getType().equals("teacher")) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}