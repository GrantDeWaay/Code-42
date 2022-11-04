package edu.iastate.code42;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.Const;

public class AssignmentCodeCreateActivity extends AppCompatActivity {
    private Button goNext;
    private EditText baseCode;
    private EditText newUnitTest;

    User user;
    SharedPreferences userSession;

    int courseId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_code_create);
        goNext = findViewById(R.id.goNext);
        baseCode = findViewById(R.id.baseCode);
        newUnitTest = findViewById(R.id.unitTestText);
        Assignment cc = Assignment.get(getApplicationContext());
        cc.setCode(baseCode.getText().toString());

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        courseId = getIntent().getIntExtra("courseId", -1);

        goNext.setOnClickListener(view -> {
            cc.setUnitTests(newUnitTest.getText().toString());
            cc.setCode(baseCode.getText().toString());
            JSONObject x = cc.formatJSON();
            Log.i("Mm", x.toString());
            String url = String.format(Const.CREATE_ASSIGNMENT, userSession.getString("token", ""));
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,x,
                    response -> {
                        Log.i("resp", response.toString());

                        String url2 = "";
                        try {
                            url2 = String.format(Const.ADD_ASSIGNMENT_TO_COURSE, courseId, response.get("id"),
                                    userSession.getString("token", ""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        StringRequest adAssignmentToCourse = new StringRequest(Request.Method.PUT, url2,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Assignment to Course Mapping Error:", error.toString());

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

                        AppController.getInstance().addToRequestQueue(adAssignmentToCourse, "course_get_students");
                    }, error -> {
                Log.i("resp", error.toString());
            });
            AppController.getInstance().addToRequestQueue(req);
        });
    }
}