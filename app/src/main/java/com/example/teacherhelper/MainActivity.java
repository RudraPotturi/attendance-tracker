package com.example.teacherhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                finish();
                startActivity(new Intent(this, AboutActivity.class));
                return true;

            case R.id.item2:
                finish();
                startActivity(new Intent(this, HelpActivity.class));
                return true;

            case R.id.item3:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        ImageView attendimg = findViewById(R.id.attendance);
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

            ImageView cgpaimg = findViewById(R.id.cgpa);
            cgpaimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent sendIn = new Intent(MainActivity.this, GPA.class);
                        startActivity(sendIn);
                    } catch (Exception e) {

                    }
                }
            });


        ImageView timetab = findViewById(R.id.timetable);
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

        ImageView profilebtn = findViewById(R.id.profile);
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
