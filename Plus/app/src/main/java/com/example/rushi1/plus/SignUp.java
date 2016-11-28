package com.example.rushi1.plus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUp extends AppCompatActivity {
    Button su;
    EditText name,username,password,mob, email,region;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private DatabaseReference pointer = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign-Up");
        name=(EditText)findViewById(R.id.editText5suname);
        username=(EditText)findViewById(R.id.editText6suusername);
        password=(EditText)findViewById(R.id.editText7supass);
        mob=(EditText)findViewById(R.id.editText8sumob);
        email=(EditText)findViewById(R.id.editText9sumail);
        region=(EditText)findViewById(R.id.editText10region);
        su=(Button)findViewById(R.id.button3signup);
        progressDialog=new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Registering you...");
        auth = FirebaseAuth.getInstance();

        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString=email.getText().toString();
                String passString=password.getText().toString();

                if (TextUtils.isEmpty(emailString)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passString)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user
                auth.createUserWithEmailAndPassword(emailString, passString)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.show();
                                Toast.makeText(SignUp.this, "You are now registered", Toast.LENGTH_SHORT).show();

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    final FirebaseUser user = task.getResult().getUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username.getText().toString())
                                            .setPhotoUri(null)
                                            .build();
                                    user.updateProfile(profileUpdates);

                                    Map<String,Object> up = new HashMap<String, Object>();
                                    String x = pointer.push().getKey();
                                    pointer.updateChildren(up);

                                    DatabaseReference tempPointer = pointer.child(x);
                                    Map<String,Object> up2 = new HashMap<String, Object>();
                                    up2.put("dispname",username.getText().toString());
                                    up2.put("email",email.getText().toString());
                                    up2.put("mobile",mob.getText().toString());
                                    up2.put("region",region.getText().toString());
                                    up2.put("password",password.getText().toString());
                                    tempPointer.updateChildren(up2);

                                    startActivity(new Intent(SignUp.this, Profile.class));
                                    finish();
                                    progressDialog.dismiss();
                                }
                            }
                        });

            }
        });


    }
}
