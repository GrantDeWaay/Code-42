package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AssignmentCreateActivity extends AppCompatActivity {
    private Button btnClick;
    private EditText textE;
    private TextView textx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_create);
        btnClick = findViewById(R.id.button);
        textE = findViewById(R.id.enterText);
        textx = findViewById(R.id.textView);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btnClick.setOnClickListener(v -> {
            makeJsonObjReq();
            Log.i("Click", textE.getText().toString());
            // Do something in response to button click
        });
    }

    private void makeJsonObjReq() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.56.1:8080/HelloWorld?username=Andrew";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    textx.setText("Response is: " + response);
                }, error -> {
                    textx.setText("That didn't work!: " + error);
                });
        queue.add(stringRequest);
    }
}