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

    private Button mBtn_signin;
    private Button mBtn_signup;
    private Button mBtn_guest;
    private EditText mEt_username;
    private EditText mEt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn_signin.findViewById(R.id.btn_main_signin);
        mBtn_signup.findViewById(R.id.btn_main_signup);
        mBtn_guest.findViewById(R.id.btn_main_guest);
        mEt_username.findViewById(R.id.et_main_username);
        mEt_password.findViewById(R.id.et_main_password);


    }

    public void onClick(View view){
        String username = mEt_username.getText().toString();
        String password = mEt_password.getText().toString();

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