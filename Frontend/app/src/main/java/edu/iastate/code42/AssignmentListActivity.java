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
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.AssignmentListAdapter;
import edu.iastate.code42.utils.Const;

/**
 * AssignmentListActivity class
 * View with a list of Assignments for a certain Course
 * Layout: activity_assignment_list
 * @author Andrew
 */
public class AssignmentListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListView assignmentList;
    FloatingActionButton addAssignment;

    ArrayList<Assignment> assignments;
    AssignmentListAdapter assignmentAdapter;

    User user;
    SharedPreferences userSession;

    int courseId;

    /**
     * Creates and draws the view; initializes the objects
     * Performs GET_ASSIGNMENTS_FOR_COURSE HTTP Request
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!userSession.contains("token")){
            Intent login = new Intent(this, MainActivity.class);
            startActivity(login);
        }
        courseId = getIntent().getIntExtra("courseId", -1);

        assignmentList = findViewById(R.id.listAssignments);
        assignmentList.setOnItemClickListener(this);

        addAssignment = findViewById(R.id.addAssignment);
        addAssignment.setOnClickListener(this);

        if(user.getType() == "student"){
            addAssignment.setVisibility(View.INVISIBLE);
        }else{
            addAssignment.setVisibility(View.VISIBLE);
        }

        String url = String.format(Const.GET_ASSIGNMENTS_FOR_COURSE, courseId, userSession.getString("token", ""));
        assignments = new ArrayList<>();

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

                if(assignments.size() > 0){
                    assignmentAdapter = new AssignmentListAdapter(getApplicationContext(), assignments);
                    assignmentList.setAdapter(assignmentAdapter);
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
    }

    /**
     * Event Handler for when item in the ListView selected; Opens AssignmentWorkActivity for selected Assignment
     * @param adapterView AdaperView for ListView
     * @param view ListView of item selected
     * @param i Position of item selected
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent assignmentView = new Intent(this, AssignmentWorkActivity.class);
        assignmentView.putExtra("id", assignments.get(i).getId());

        startActivity(assignmentView);
    }

    /**
     * Event handler for when Add button pressed; Opens AssignmentCreateActivity
     * @param view Button View that is Pressed
     */
    @Override
    public void onClick(View view) {
        Intent assignmentCreate = new Intent(this, AssignmentCreateActivity.class);
        assignmentCreate.putExtra("courseId", courseId);

        startActivity(assignmentCreate);
    }
}