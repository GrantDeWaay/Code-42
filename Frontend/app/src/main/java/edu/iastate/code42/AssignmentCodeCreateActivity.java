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
import edu.iastate.code42.objects.AssignmentCreationDataHolder;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.Const;

public class AssignmentCodeCreateActivity extends AppCompatActivity {
    private Button goNext;
    private EditText baseCode;
    private EditText newUnitTest;
    private Assignment cc;

    User user;
    SharedPreferences userSession;

    int courseId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_code_create);
        goNext = findViewById(R.id.goNext);
        baseCode = findViewById(R.id.baseCode);
        newUnitTest = findViewById(R.id.unitTestText);
        cc = Assignment.get(getApplicationContext());
        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        courseId = getIntent().getIntExtra("courseId", -1);

        goNext.setOnClickListener(view -> {
            AssignmentCreationDataHolder.setExpectedOut(newUnitTest.getText().toString());
            AssignmentCreationDataHolder.setCode(baseCode.getText().toString());
            AssignmentCreationDataHolder.sendAssignment(userSession.getString("token", ""),
                    getIntent().getIntExtra("courseId", -1));
        });
    }
}