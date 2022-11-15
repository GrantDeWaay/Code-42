package edu.iastate.code42;

import static edu.iastate.code42.utils.Const.*;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.utils.LanguageSpinnerAdapter;

public class AssignmentCreateActivity extends AppCompatActivity {
    private Button btnClick;
    private EditText assignmentTitle;
    private TextView desc;
    private TextView problem;
    private EditText score;
    private Spinner langSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_create);
        btnClick = findViewById(R.id.nextButton);
        assignmentTitle = findViewById(R.id.assignmentNameEnter);// assignment name
        desc = findViewById(R.id.description);
        problem = findViewById(R.id.problemStatement);
        langSpin = findViewById(R.id.spinner);
        score = findViewById(R.id.scoreNumber);

        String[] langText = new String[]{"Python", "C", "Java"};
        Integer[] langIcons = new Integer[]{R.drawable.py_lang_logo,
                R.drawable.c_lang_logo, R.drawable.java_lang_logo};

        ArrayAdapter<String> adapter = new LanguageSpinnerAdapter(this, langText, langIcons);
        langSpin.setAdapter(adapter);
        btnClick.setOnClickListener(v -> {

            if (assignmentTitle.getText().length() < 2){
                Toast.makeText(getApplicationContext(), "Invalid Assignment Name", Toast.LENGTH_LONG).show();
            }
            else if (desc.getText().length() < 2){
                Toast.makeText(getApplicationContext(), "Invalid Description", Toast.LENGTH_LONG).show();
            }
            else if (problem.getText().length() < 2){
                Toast.makeText(getApplicationContext(), "Invalid Problem", Toast.LENGTH_LONG).show();
            }
            else if (score.getText().length() == 0){
                Toast.makeText(getApplicationContext(), "Please enter the point value of the assignment", Toast.LENGTH_LONG).show();
            }
            else{
                Intent i = new Intent(this, AssignmentCodeCreateActivity.class);

                Assignment cc = new Assignment(getApplicationContext());
                cc.setAssignmentName(assignmentTitle.getText().toString());
                cc.setDescription(desc.getText().toString());
                cc.setStatement(problem.getText().toString());
                cc.setPoints(Integer.parseInt(score.getText().toString()));
                cc.setLang(langText[langSpin.getSelectedItemPosition()]);

                i.putExtra("courseId", getIntent().getIntExtra("courseId", -1));
                startActivity(i);
            }
        });
    }
/*
    private void makeJsonObjReq() throws JSONException {
        // Instantiate the RequestQueue.
        JSONObject obj = new JSONObject();
        obj.put("title", assignmentTitle.getText());
        obj.put("description", desc.getText());
        obj.put("problemStatement", problem.getText());
        String url = SOURCE + CREATE_ASSIGNMENT;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,obj,
                response -> {
                    resp = response.toString();
                }, error -> {
            resp = error.toString();
        });
        AppController.getInstance().addToRequestQueue(req);
    }
    */
}
