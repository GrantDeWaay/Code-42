package edu.iastate.counter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button next;
    Button increaseBtn;
    Button decreaceBtn;
    TextView numberTxt;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = findViewById(R.id.next);

        increaseBtn = findViewById(R.id.increaseBtn);
        decreaceBtn = findViewById(R.id.decreaseBtn);
        numberTxt = findViewById(R.id.homeCounter);

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

        decreaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                numberTxt.setText(String.valueOf(--counter));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(counter > 0){
                    Intent up = new Intent(MainActivity.this, UpCounter.class);
                    up.putExtra("counter", counter);
                    startActivity(up);
                }else if(counter < 0){
                    Intent down = new Intent(MainActivity.this, DownCounter.class);
                    down.putExtra("counter", counter);
                    startActivity(down);
                }else{
                    Toast.makeText(getApplicationContext(), "Counter can't be zero", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}