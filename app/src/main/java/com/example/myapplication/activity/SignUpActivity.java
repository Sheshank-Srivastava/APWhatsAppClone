package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_email,et_username,et_password;
    Button btn_signUp,btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email = findViewById(R.id.et_emailsignUp);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        btn_signUp = findViewById(R.id.btn_SignupEnter);
        btn_Login = findViewById(R.id.btn_LogSignup);


        btn_signUp.setOnClickListener(this);
        btn_Login .setOnClickListener(this);

        if (ParseUser.getCurrentUser()!=null){
            transitionActivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_SignupEnter:
                final String email = et_email.getText().toString().trim();
                final String username = et_username.getText().toString().trim();
                final String password = et_password.getText().toString().trim();


                ParseUser parseUser = new ParseUser();
                parseUser.setEmail(email);
                parseUser.setUsername(username);
                parseUser.setPassword(password);

                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Signing Up");
                dialog.show();
                parseUser.signUpInBackground(e -> {
                    dialog.dismiss();
                    if (e != null) return;
                    Toast.makeText(this, parseUser.getUsername()+" is Signed up...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this,WhatsAppActivity.class));
                    finish();
                });
                break;
            case R.id.btn_LogSignup:

                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }
    public void transitionActivity(){
        startActivity(new Intent(SignUpActivity.this,WhatsAppActivity.class));
        finish();
    }
}
