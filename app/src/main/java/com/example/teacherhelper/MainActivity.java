package com.example.teacherhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);

        ImageButton attendimg = findViewById(R.id.attendance);
        attendimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent sendIn = new Intent(MainActivity.this, AttendanceActivity.class);
                    startActivityForResult(sendIn, 3);
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

        ImageButton timetab = findViewById(R.id.timetab);
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

        ImageButton profilebtn = findViewById(R.id.profilebtn);
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

    @Override
    public void onClick(View view) {

        if (view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
