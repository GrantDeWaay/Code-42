package edu.iastate.code42;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Arrays;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.Assignment;

public class AssignmentCodeCreateActivity extends AppCompatActivity {
    private Button goNext;
    private EditText baseCode;
    private EditText newUnitTest;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_code_create);
        goNext = findViewById(R.id.goNext);
        baseCode = findViewById(R.id.baseCode);
        newUnitTest = findViewById(R.id.unitTestText);
        Assignment cc = Assignment.get(getApplicationContext());
        cc.setCode(baseCode.getText().toString());
        goNext.setOnClickListener(view -> {
            cc.setUnitTests(newUnitTest.getText().toString());
            cc.setCode(baseCode.getText().toString());
            JSONObject x = cc.formatJSON();
            Log.i("Mm", x.toString());
            String url = "http://coms-309-028.class.las.iastate.edu:8080/assignment/create?token=test";
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,x,
                    response -> {
                        Log.i("resp", response.toString());
                    }, error -> {
                Log.i("resp", error.toString());
            });
            AppController.getInstance().addToRequestQueue(req);
        });
    }
}