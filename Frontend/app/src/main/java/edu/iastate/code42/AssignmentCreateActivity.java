package edu.iastate.code42;

import static java.security.AccessController.getContext;
import static edu.iastate.code42.utils.Const.*;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import edu.iastate.code42.app.AppController;

public class AssignmentCreateActivity extends AppCompatActivity {
    private Button btnClick;
    private EditText assignmentTitle;
    private TextView desc;
    private TextView problem;
    private String resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_create);
        btnClick = findViewById(R.id.button);
        assignmentTitle = findViewById(R.id.assignmentNameEnter);// assignment name
        desc = findViewById(R.id.description);
        problem = findViewById(R.id.problemStatement);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lang_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        btnClick.setOnClickListener(v -> {
            try {
                makeJsonObjReq();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Click", assignmentTitle.getText().toString());
            Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
        });
    }

    private void makeJsonObjReq() throws JSONException {
        // Instantiate the RequestQueue.
        JSONObject obj = new JSONObject();
        obj.put("title", assignmentTitle.getText());
        obj.put("description", desc.getText());
        obj.put("problemStatement", problem.getText());
        String url = SOURCE;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,obj,
                response -> {
                    resp = response.toString();
                }, error -> {
            resp = error.toString();
                });
        AppController.getInstance().addToRequestQueue(req);
    }
}