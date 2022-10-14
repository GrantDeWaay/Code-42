package edu.iastate.code42;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AssignmentCodeCreateActivity extends AppCompatActivity {
    private Button goNext;
    private EditText teacherBaseCode;
    private EditText studentBaseCode;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_code_create);
        goNext = findViewById(R.id.goNext);
        teacherBaseCode = findViewById(R.id.teacherBaseCode);
        studentBaseCode = findViewById(R.id.studentBaseCode);

        teacherBaseCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                studentBaseCode.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
