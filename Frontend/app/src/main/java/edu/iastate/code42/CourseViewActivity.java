package edu.iastate.code42;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityCourseViewBinding;
import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.AssignmentListAdapter;
import edu.iastate.code42.utils.BaseBack;
import edu.iastate.code42.utils.Const;
import edu.iastate.code42.utils.UserListAdapter;

/**
 * CourseViewActivity class
 * Screen to view and edit details about individual Course
 * Layout: activity_course_view
 * Extends BaseDrawer
 * @author Andrew
 */
public class CourseViewActivity extends BaseBack implements View.OnClickListener, AdapterView.OnItemClickListener {

    ActivityCourseViewBinding activityBaseBackBinding;
    EditText title;
    EditText description;
    EditText language;
    FloatingActionButton edit;
    Button delete;

    TableLayout assignmentLayout;
    ListView assignmentList;
    Button addAssignment;
    Button moreAssigment;

    TableLayout studentLayout;
    ListView studentList;
    Button addStudent;
    Button moreStudent;

    TableLayout teacherLayout;
    ListView teacherList;
    Button addTeacher;
    Button moreTeacher;

    User user;
    SharedPreferences userSession;

    boolean viewState;
    int courseId;

    ArrayList<Assignment> assignments;
    ArrayList<User> students;
    ArrayList<User> teachers;

    AssignmentListAdapter assignmentAdapter;
    UserListAdapter studentAdapter;
    UserListAdapter teacherAdapter;

    /**
     * Creates and draws the view; initializes the objects
     * Performs GET_COURSE, GET_ASSIGNMENTS_FOR_COURSE, GET_TEACHERS_FOR_COURSE, and
     * GET_STUDENTS_FOR_COURSE HTTP Requests
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseBackBinding = ActivityCourseViewBinding.inflate(getLayoutInflater());
        setContentView(activityBaseBackBinding.getRoot());
        allocateActivityTitle("");

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!userSession.contains("token")){
            Intent login = new Intent(CourseViewActivity.this, MainActivity.class);
            startActivity(login);
        }
        setPreviousScreen(new Intent(CourseViewActivity.this, CoursesActivity.class));

        title = activityBaseBackBinding.getRoot().findViewById(R.id.courseTitleHeader);
        description = activityBaseBackBinding.getRoot().findViewById(R.id.courseDescriptionView);
        language = activityBaseBackBinding.getRoot().findViewById(R.id.courseLanguagesView);
        edit = activityBaseBackBinding.getRoot().findViewById(R.id.floatingEditCourse);
        delete = activityBaseBackBinding.getRoot().findViewById(R.id.courseDelete);

        assignmentLayout = activityBaseBackBinding.getRoot().findViewById(R.id.assignmentLayout);
        assignmentList = activityBaseBackBinding.getRoot().findViewById(R.id.assignmentList);
        addAssignment = activityBaseBackBinding.getRoot().findViewById(R.id.addAssignmentButton);
        moreAssigment = activityBaseBackBinding.getRoot().findViewById(R.id.moreAssignmentButton);

        studentLayout = activityBaseBackBinding.getRoot().findViewById(R.id.studentLayout);
        studentList = activityBaseBackBinding.getRoot().findViewById(R.id.studentList);
        addStudent = activityBaseBackBinding.getRoot().findViewById(R.id.addStudentButton);
        moreStudent = activityBaseBackBinding.getRoot().findViewById(R.id.moreStudentButton);

        teacherLayout = activityBaseBackBinding.getRoot().findViewById(R.id.teacherLayout);
        teacherList = activityBaseBackBinding.getRoot().findViewById(R.id.teacherList);
        addTeacher = activityBaseBackBinding.getRoot().findViewById(R.id.addTeacherButton);
        moreTeacher = activityBaseBackBinding.getRoot().findViewById(R.id.moreTeacherButton);


        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        addAssignment.setOnClickListener(this);
        moreAssigment.setOnClickListener(this);

        addStudent.setOnClickListener(this);
        moreStudent.setOnClickListener(this);

        addTeacher.setOnClickListener(this);
        moreTeacher.setOnClickListener(this);

        assignmentList.setOnItemClickListener(this);
        studentList.setOnItemClickListener(this);
        teacherList.setOnItemClickListener(this);

        viewState = true;

        updateViewState(viewState);

        if(getIntent().hasExtra("courseId")){
            courseId = getIntent().getIntExtra("courseId",0);

            assignments = new ArrayList<>();
            students = new ArrayList<>();
            teachers = new ArrayList<>();

            assignmentAdapter = new AssignmentListAdapter(getApplicationContext(), assignments);
            assignmentList.setAdapter(assignmentAdapter);

            studentAdapter = new UserListAdapter(getApplicationContext(), students);
            studentList.setAdapter(studentAdapter);

            teacherAdapter = new UserListAdapter(getApplicationContext(), teachers);
            teacherList.setAdapter(teacherAdapter);

            getCourseDetails();

            getCourseAssignments();

            if(!user.getType().equals("student")) {
                getCourseStudents();

                if(user.getType().equals("admin")) {
                    getCourseTeachers();
                }
            }
        }else{
            Intent courseList = new Intent(this, CoursesActivity.class);
            startActivity(courseList);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        updateViewState(viewState);
        getCourseDetails();

        getCourseAssignments();

        if(!user.getType().equals("student")) {
            getCourseStudents();

            if(user.getType().equals("admin")) {
                getCourseTeachers();
            }
        }
    }

    /**
     * Event handler for when any button is pressed; Opens various activities depending on button
     * @param view Button View that is Pressed
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.floatingEditCourse:
                if(viewState){
                    viewState = false;
                }else{
                    updateCourseDetails();

                    viewState = true;
                }

                updateViewState(viewState);
                break;

            case R.id.courseDelete:
                deleteCourse();
                break;

            case R.id.addAssignmentButton:
                Intent assignmentCreate = new Intent(this, AssignmentCreateActivity.class);
                assignmentCreate.putExtra("courseId", courseId);

                startActivity(assignmentCreate);
                break;

            case R.id.moreAssignmentButton:
                Intent assignmentListIntent = new Intent(this, AssignmentListActivity.class);
                assignmentListIntent.putExtra("courseId", courseId);

                startActivity(assignmentListIntent);
                break;

            case R.id.addStudentButton:
                Intent userListStudentAdd = new Intent(this, UserListActivity.class);
                userListStudentAdd.putExtra("courseId", courseId);
                userListStudentAdd.putExtra("type", 2);

                startActivity(userListStudentAdd);
                break;

            case R.id.moreStudentButton:
                Intent userListStudent = new Intent(this, UserListActivity.class);
                userListStudent.putExtra("courseId", courseId);
                userListStudent.putExtra("type", 4);

                startActivity(userListStudent);
                break;

            case R.id.addTeacherButton:
                Intent userListTeacherAdd = new Intent(this, UserListActivity.class);
                userListTeacherAdd.putExtra("courseId", courseId);
                userListTeacherAdd.putExtra("type", 1);

                startActivity(userListTeacherAdd);
                break;

            case R.id.moreTeacherButton:
                Intent userListTeacher = new Intent(this, UserListActivity.class);
                userListTeacher.putExtra("courseId", courseId);
                userListTeacher.putExtra("type", 3);

                startActivity(userListTeacher);
                break;
        }
    }

    /**
     * Event Handler for when item in a ListView selected; Opens various activities depending on ListView
     * @param adapterView AdaperView for ListView
     * @param view ListView of item selected
     * @param i Position of item selected
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch(view.getId()){
            case R.id.assignmentList:
                Intent assignmentView = new Intent(this, AssignmentWorkActivity.class);
                assignmentView.putExtra("id", assignments.get(i).getId());

                startActivity(assignmentView);
                break;

            case R.id.teacherList:
                break;

            case R.id.studentList:
                break;
        }
    }

    private void updateViewState(Boolean state){
        if(state){
            setSave(false);

            title.setEnabled(false);
            description.setEnabled(false);
            language.setEnabled(false);
            delete.setVisibility(View.INVISIBLE);

            assignmentLayout.setVisibility(View.VISIBLE);
            edit.setImageDrawable(getDrawable(R.drawable.ic_edit_foreground));

            if(user.getType().equals("student")){
                edit.setVisibility(View.INVISIBLE);
                edit.setEnabled(false);
                studentLayout.setVisibility(View.INVISIBLE);
                teacherLayout.setVisibility(View.INVISIBLE);
                addAssignment.setVisibility(View.INVISIBLE);
            }else{
                edit.setVisibility(View.VISIBLE);
                edit.setEnabled(true);
                studentLayout.setVisibility(View.VISIBLE);
                addAssignment.setVisibility(View.VISIBLE);
                if(user.getType().equals("admin")){
                    teacherLayout.setVisibility(View.VISIBLE);
                }else{
                    teacherLayout.setVisibility(View.INVISIBLE);
                }
            }
        }else{
            setSave(true);

            title.setEnabled(true);
            description.setEnabled(true);
            language.setEnabled(true);

            delete.setVisibility(View.VISIBLE);
            edit.setImageDrawable(getDrawable(R.drawable.ic_save_foreground));

            studentLayout.setVisibility(View.INVISIBLE);
            teacherLayout.setVisibility(View.INVISIBLE);
            assignmentLayout.setVisibility(View.INVISIBLE);
        }

    }

    private void deleteCourse(){
        String url = String.format(Const.DELETE_COURSE, courseId,
                userSession.getString("token", ""));

        StringRequest deleteReq = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent courseList = new Intent(CourseViewActivity.this, CoursesActivity.class);
                        startActivity(courseList);
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

        AppController.getInstance().addToRequestQueue(deleteReq, "delete_course_req");
    }

    private void updateCourseDetails(){
        String url = String.format(Const.UPDATE_COURSE,courseId, userSession.getString("token", ""));
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("id", courseId);
            jsonBody.put("title", title.getText());
            jsonBody.put("description", description.getText());
            jsonBody.put("languages", language.getText());
        } catch (JSONException e) {
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

        AppController.getInstance().addToRequestQueue(updateReq, "update_course_req");
    }

    private void getCourseDetails(){
        String url = String.format(Const.GET_COURSE, courseId, userSession.getString("token", ""));

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
    }

    private void getCourseAssignments(){
        assignments.clear();
        String url = String.format(Const.GET_ASSIGNMENTS_FOR_COURSE, courseId, userSession.getString("token", ""));

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

                assignmentAdapter.notifyDataSetChanged();
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
    }

    private void getCourseStudents(){
        students.clear();
        String url = String.format(Const.GET_STUDENTS_FOR_COURSE, courseId, userSession.getString("token", ""));

        JsonArrayRequest courseStudentsReq = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        User u = new User(response.getJSONObject(i));

                        students.add(u);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                studentAdapter.notifyDataSetChanged();
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

    private void getCourseTeachers(){
        teachers.clear();
        String url = String.format(Const.GET_TEACHERS_FOR_COURSE, courseId, userSession.getString("token", ""));

        JsonArrayRequest courseTeacherReq = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        User u = new User(response.getJSONObject(i));

                        teachers.add(u);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                teacherAdapter.notifyDataSetChanged();
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

        AppController.getInstance().addToRequestQueue(courseTeacherReq, "course_get_teachers");
    }
}