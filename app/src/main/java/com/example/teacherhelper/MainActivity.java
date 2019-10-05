package com.example.teacherhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton attendimg = findViewById(R.id.attendance);
        attendimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent sendIn = new Intent(MainActivity.this, AttendanceActivity.class);
                    startActivityForResult(sendIn, 2);
                } catch (Exception e) {

                }
            }
        });

        ImageButton cgpaimg = findViewById(R.id.cgpa);
        cgpaimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent sendIn = new Intent(MainActivity.this, CGPA.class);
                    startActivityForResult(sendIn, 2);
                } catch (Exception e) {

                }
            }
        });

        ImageButton timetab = findViewById(R.id.cgpa);
        timetab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent sendIn = new Intent(MainActivity.this, Timetable.class);
                    startActivityForResult(sendIn, 2);
                } catch (Exception e) {

                }
            }
        });

        ImageButton profilebtn = findViewById(R.id.cgpa);
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent sendIn = new Intent(MainActivity.this, Account.class);
                    startActivityForResult(sendIn, 2);
                } catch (Exception e) {

                }
            }
        });
    }
}
