package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mBtnSignin;
    private Button mBtnSignup;          // Sign up has not been implemented yet.
    private Button mBtnGuest;
    private EditText mEtUsername;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnSignin=findViewById(R.id.btn_main_signin);
        mBtnSignup=findViewById(R.id.btn_main_signup);
        mBtnGuest=findViewById(R.id.btn_main_guest);
        mEtUsername=findViewById(R.id.et_main_username);
        mEtPassword=findViewById(R.id.et_main_password);

        mBtnSignin.setOnClickListener(this::onClick);
        mBtnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view){
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();

        // Pop Out Msg
        String sucMsg   = "You successfully Signed in.";
        String failMsg  = "The username or password you’ve entered is incorrect.";

        Intent intent   = null;
        Map<String, String> userDict = new HashMap<String, String>();

//User Dictionary Needs to be updated. This feature is not implemented.
        userDict.put("user","pswd");

        if (userDict.containsKey(username) && password.equals(userDict.get(username))){
            Toast.makeText(MainActivity.this, sucMsg, Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this, failMsg, Toast.LENGTH_SHORT).show();
        }


    }
}