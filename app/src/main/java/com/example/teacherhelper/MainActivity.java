package com.example.teacherhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button moreMealButton = findViewById(R.id.moreMealsButton);
        moreMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent sendIn = new Intent(MainActivity.this, AttendanceActivity.class);
                    startActivityForResult(sendIn, 2);
                } catch (Exception e) {

                }
            }
        });
    }
}
