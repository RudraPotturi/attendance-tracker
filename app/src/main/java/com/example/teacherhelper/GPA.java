package com.example.teacherhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GPA extends AppCompatActivity {

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.common_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()){
      case R.id.item1:
        finish();
        startActivity(new Intent(this, MainActivity.class));
        return true;

    }
    return super.onOptionsItemSelected(item);
  }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);


      Button btn =(Button) findViewById(R.id.findbtn);




      btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          EditText editText1 = (EditText) findViewById(R.id.et1);
          EditText editText2 = (EditText) findViewById(R.id.et2);
          EditText editText3 = (EditText) findViewById(R.id.et3);
          EditText editText4 = (EditText) findViewById(R.id.et4);
          EditText editText5 = (EditText) findViewById(R.id.et5);

          double sg1 = Double.valueOf(editText1.getText().toString());
          double sg2 = Double.valueOf(editText2.getText().toString());
          double sg3 = Double.valueOf(editText3.getText().toString());
          double sg4 = Double.valueOf(editText4.getText().toString());
          double sg5 = Double.valueOf(editText5.getText().toString());

          double result = (sg1+sg2+sg3+sg4+sg5)/5.0;

          if(sg1==0 && sg2==0 && sg3==0 && sg4==0 && sg5==0) {
            Toast.makeText(getApplicationContext(), "Insufficient Data ", Toast.LENGTH_LONG).show();
          }
          else if ((sg1>4.0) || (sg2>4.0) || (sg3>4.0) || (sg4>4.0) || (sg5>4.0)){
            Toast.makeText(getApplicationContext(), " Invalid GPA", Toast.LENGTH_SHORT).show();
          }
         
          else {
            Intent intent = new Intent(getApplicationContext(), GpaResultActivity.class);
            intent.putExtra("final_gpa", result);
            startActivity(intent);
          }


        }
      });





    }
}
