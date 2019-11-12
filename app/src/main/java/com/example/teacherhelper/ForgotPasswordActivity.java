package com.example.teacherhelper;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private EditText editTextUserEmail;
    private Button buttonForgotPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        progressDialog = new ProgressDialog(this);
        editTextUserEmail = (EditText) findViewById(R.id.editTextUserEmail);
        buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();



        buttonForgotPassword.setOnClickListener(this);
    }

    private void forgotPassword(){
        String email = editTextUserEmail.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Sending link...");
        progressDialog.show();



        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this,"Reset link sent successfully",Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        else{
                            Toast.makeText(ForgotPasswordActivity.this,"Invalid email",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonForgotPassword){
            forgotPassword();
        }

    }


}
