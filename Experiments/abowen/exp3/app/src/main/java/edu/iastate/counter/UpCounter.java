package edu.iastate.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UpCounter extends AppCompatActivity {

    Button back;
    Button increaseBtn;
    TextView numberTxt;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_counter);

        back = findViewById(R.id.back);

        increaseBtn = findViewById(R.id.increaseBtn2);
        numberTxt = findViewById(R.id.upCounter);

        if(getIntent().hasExtra("counter")){
            counter = getIntent().getExtras().getInt("counter");
        }
        numberTxt.setText(String.valueOf(counter));


        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                numberTxt.setText(String.valueOf(++counter));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(UpCounter.this, MainActivity.class);
                back.putExtra("counter", counter);
                startActivity(back);
            }
        });
    }
}