package edu.iastate.code42;

import static edu.iastate.code42.utils.Const.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import edu.iastate.code42.app.AppController;

public class AssignmentCreateActivity extends AppCompatActivity {
    private Button btnClick;
    private EditText textE;
    private TextView textx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_create);
        btnClick = findViewById(R.id.button);
        textE = findViewById(R.id.assignmentNameEnter);// assignment name
        textx = findViewById(R.id.textx);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lang_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        btnClick.setOnClickListener(v -> {
            makeJsonObjReq();
            Log.i("Click", textE.getText().toString());
            // Do something in response to button click
        });
    }

    private void makeJsonObjReq() {
        // Instantiate the RequestQueue.
        String url = HELLO_WORLD + "?username=" + textE.getText();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    textx.setText("Response is: " + response);
                }, error -> {
                    textx.setText("That didn't work!: " + error);
                });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}