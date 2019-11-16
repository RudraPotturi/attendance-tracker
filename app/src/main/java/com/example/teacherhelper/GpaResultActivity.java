package com.example.teacherhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GpaResultActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gpa_result);


    TextView tv =(TextView) findViewById(R.id.tv);

    try {

      Bundle b = getIntent().getExtras();
      double final_gpa = b.getDouble("final_gpa");

        tv.setText("Your GPA is "+final_gpa);

    } catch (Exception e) {
      Toast.makeText(getBaseContext(), "Exception Occured", Toast.LENGTH_LONG).show();
    }
  }
}
