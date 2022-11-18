package edu.iastate.code42;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.objects.AssignmentCreationDataHolder;
import edu.iastate.code42.objects.User;

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
        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        courseId = getIntent().getIntExtra("courseId", -1);

        goNext.setOnClickListener(view -> {
            AssignmentCreationDataHolder.setExpectedOut(newUnitTest.getText().toString());
            AssignmentCreationDataHolder.setCode(baseCode.getText().toString());
            AssignmentCreationDataHolder.sendAssignment(getApplicationContext(),userSession.getString("token", ""), courseId);
            finish();
        });
    }
}