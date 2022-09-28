package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AssignmentCreateActivity extends AppCompatActivity {
    private Button btnClick;
    private EditText textE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_create);
        btnClick = findViewById(R.id.button);
        textE = findViewById(R.id.enterText);
        btnClick.setOnClickListener(v -> {
            Log.i("Click", textE.getText().toString());
            // Do something in response to button click
        });
    }
}