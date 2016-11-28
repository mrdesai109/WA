package com.example.rushi1.plus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private EditText uname;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog,progressDialog2;
    private DatabaseReference pointer = FirebaseDatabase.getInstance().getReference().child("users");
    private Button fp;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog2 = new ProgressDialog(MainActivity.this);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
       // uname = (EditText) findViewById(R.id.uname);
       // btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        fp=(Button)findViewById(R.id.btn_reset_password);
        tv2=(TextView)findViewById(R.id.textView6);

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(MainActivity.this,ForgotPass.class);
                startActivity(x);
            }
        });


        progressDialog.setMessage("Registering you...");
        progressDialog2.setMessage("Logging in...");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog2.show();
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog2.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                        } else {

                            Intent intent = new Intent(MainActivity.this, Profile.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

       tv2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent x = new Intent(MainActivity.this,SignUp.class);
               startActivity(x);
           }
       });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
