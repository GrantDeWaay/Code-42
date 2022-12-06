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
                .replaceAll(" ", "_").toLowerCase();
        String lang = AssignmentCreationDataHolder.getLang();
        String startingCode;
        switch (lang) {
            case "Java":
                startingCode = "import java.util.Scanner;\n\n" +
                                className + " {\n" +
                                "\tpublic static void main(String[] args) {\n" +
                                "\t\tScanner myObj = new Scanner(System.in);\n" +
                                "\t\tint x = myObj.nextInt();\n" +
                                "\t\tSystem.out.println(\"Value: \" + x)\n" +
                                "\t}\n" +
                                "}";
                baseCode.setText(startingCode);
                break;
            case "Go":
                startingCode = "package main\n\n" +
                        "import \"fmt\"\n\n" +
                        "func main() {\n" +
                        "\tvar x int\n" +
                        "\tfmt.Scanln(&x)\n" +
                        "\tfmt.Print(\"print output here!\")\n" +
                        "}";
                baseCode.setText(startingCode);
                break;
            case "C":
                startingCode = "#include <stdio.h>\n\n" +
                                "void main() {\n" +
                                "\tint x;\n" +
                                "\tscanf(\"%d\", &x);\n" +
                                "\tprintf(\"print output here!\");\n" +
                                "}";
                baseCode.setText(startingCode);
                break;
            case "Python":
                startingCode = "x = input(\"Enter your value: \")\n" +
                                "print(\"print output here!\")";
                baseCode.setText(startingCode);
                break;
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