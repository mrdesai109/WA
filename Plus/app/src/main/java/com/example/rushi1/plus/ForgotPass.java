package com.example.rushi1.plus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email;
    private Button reset;
    private TextView tv;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        setTitle("Password Reset");
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(ForgotPass.this);
        pd.setTitle("Sending you an Email...");
        email=(EditText)findViewById(R.id.editText4);
        reset=(Button)findViewById(R.id.button3rp);
        tv=(TextView)findViewById(R.id.textView5);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPass.this,MainActivity.class));
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS = email.getText().toString();
                pd.show();
                auth.sendPasswordResetEmail(emailS).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPass.this,"We have sent you an Email. Have a look",Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(ForgotPass.this,"Failed!",Toast.LENGTH_LONG).show();
                        }
                        pd.dismiss();
                    }
                });
            }
        });

    }
}
