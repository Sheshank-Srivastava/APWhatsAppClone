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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_email, et_password;

    Button btn_SignUp, btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_emaillogin);
        et_password = findViewById(R.id.et_password);

        btn_Login = findViewById(R.id.btn_LoginEnter);
        btn_SignUp = findViewById(R.id.btn_signup);

        btn_SignUp.setOnClickListener(this);
        btn_Login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_LoginEnter:

                final String email = et_email.getText().toString().trim();
                final String password = et_password.getText().toString().trim();

                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Logging in");
                dialog.setCancelable(false);
                dialog.show();

                ParseUser parseUser = new ParseUser();
                parseUser.logInInBackground(email, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dialog.dismiss();
                        if (user==null || e!=null) return;
                        Toast.makeText(LoginActivity.this, "Log in s sucessful", Toast.LENGTH_SHORT).show();

                    }
                });
                return;
            case R.id.btn_signup:
                startActivity(new Intent(this,SignUpActivity.class));
                finish();
                return;
            default:
                return;
        }
    }
}
