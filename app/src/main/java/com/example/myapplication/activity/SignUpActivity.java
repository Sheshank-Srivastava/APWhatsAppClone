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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_Email,et_UserName,et_Password;
    Button btn_SignUp,btn_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variable defination
        et_Email = findViewById(R.id.et_emailsignUp);
        et_UserName = findViewById(R.id.et_username);
        et_Password = findViewById(R.id.et_password);

        btn_SignUp = findViewById(R.id.btn_SignupEnter);
        btn_Login = findViewById(R.id.btn_LogSignup);

        btn_SignUp.setOnClickListener(this);
        btn_Login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_SignupEnter:
                final String email = et_Email.getText().toString().trim();
                final String name = et_UserName.getText().toString().trim();
                final String password = et_Password.getText().toString().trim();
                if (email.equals("")||name.equals("")||password.equals("")){
                    Toast.makeText(this, "Email,UserName, Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Signing Up");
                dialog.setCancelable(false);
                dialog.show();

                ParseUser parseUser = new ParseUser();
                parseUser.setEmail(email);
                parseUser.setUsername(name);
                parseUser.setPassword(password);
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        dialog.dismiss();
                        if (e!= null)return;
                        Toast.makeText(SignUpActivity.this, parseUser.getUsername()+" is Signed Up", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.btn_LogSignup:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
}
