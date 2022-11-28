package edu.iastate.code42;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import edu.iastate.code42.objects.AssignmentCreationDataHolder;
import edu.iastate.code42.objects.User;

public class AssignmentCodeCreateActivity extends AppCompatActivity {
    private Button goNext, AddUnitTest;
    private EditText baseCode;
    private EditText newUnitTest;
    private UnitTestCustomAdapter mAppAdapter;
    private RecyclerView UnitTestRecyclerView;

    User user;
    SharedPreferences userSession;

    int courseId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_code_create);
        UnitTestRecyclerView = findViewById(R.id.UnitTestRecyclerView);
        UnitTestRecyclerView = findViewById(R.id.UnitTestRecyclerView);
        String[] array = new String[2];
        Arrays.fill(array, "");
        mAppAdapter = new UnitTestCustomAdapter(array);
        UnitTestRecyclerView.setAdapter(mAppAdapter);
        UnitTestRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        goNext = findViewById(R.id.goNext);
        AddUnitTest = findViewById(R.id.AddUnitTest);
        baseCode = findViewById(R.id.baseCode);
        newUnitTest = findViewById(R.id.unitTestText);
        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);
        String className = AssignmentCreationDataHolder.getName()
                .replaceAll(" ", "_").toLowerCase()+
                " {\n\tpublic static void main(String[] args) {\n\tSystem.out.print();\n\t}\n}";

        if (AssignmentCreationDataHolder.getLang().equals("Java")){
            baseCode.setText(className);
        }

        courseId = getIntent().getIntExtra("courseId", -1);

        goNext.setOnClickListener(view -> {
            //Multiple unit tests implementation
            //AssignmentCreationDataHolder.setExpectedOut(mAppAdapter.getUnitTests().toString());
            AssignmentCreationDataHolder.setExpectedOut(newUnitTest.getText().toString());
            AssignmentCreationDataHolder.setCode(baseCode.getText().toString());
            AssignmentCreationDataHolder.sendAssignment(getApplicationContext(),userSession.getString("token", ""), courseId);
            finish();
        });

        AddUnitTest.setOnClickListener(view -> mAppAdapter.add(""));
    }
}