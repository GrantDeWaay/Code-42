package edu.iastate.code42;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import edu.iastate.code42.objects.AssignmentCreationDataHolder;
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
        AssignmentCreationDataHolder.resetValues();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_create);
        btnClick = findViewById(R.id.nextButton);
        assignmentTitle = findViewById(R.id.assignmentNameEnter);// assignment name
        desc = findViewById(R.id.description);
        problem = findViewById(R.id.problemStatement);
        langSpin = findViewById(R.id.spinner);
        score = findViewById(R.id.scoreNumber);


        String[] langText = new String[]{"Python", "C", "Java", "Go"};

        Integer[] langIcons = new Integer[]{
                R.drawable.py_lang_logo,
                R.drawable.c_lang_logo,
                R.drawable.java_lang_logo,
                R.drawable.go_lang_logo};
        
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

                AssignmentCreationDataHolder.setName(assignmentTitle.getText().toString());
                AssignmentCreationDataHolder.setDescription(desc.getText().toString());
                AssignmentCreationDataHolder.setStatement(problem.getText().toString());
                AssignmentCreationDataHolder.setPoints(Integer.parseInt(score.getText().toString()));
                AssignmentCreationDataHolder.setLang(langText[langSpin.getSelectedItemPosition()]);

                i.putExtra("courseId", getIntent().getIntExtra("courseId", -1));

                startActivity(i);
                finish();
            }
        });
    }
}
