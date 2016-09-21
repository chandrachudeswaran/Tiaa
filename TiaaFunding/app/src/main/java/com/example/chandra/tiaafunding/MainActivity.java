package com.example.chandra.tiaafunding;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText username_edit;
    private EditText password_edit;
    private Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.tiaa);
        setTitle(" TIAA");

        username_edit = (EditText) findViewById(R.id.username);
        password_edit = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
    }


    public void doLogin(View view) {

        if(username_edit.getText().length()==0 || password_edit.getText().length()==0){
            Toast.makeText(MainActivity.this,"Please enter all details to login",Toast.LENGTH_SHORT).show();
        }else{
            if(username_edit.getText().toString().equals("tiaa") && password_edit.getText().toString().equals("tiaa")){
                Toast.makeText(MainActivity.this,"Welcome John Doe",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Home.class);
                startActivity(intent);
            }
        }
    }



}
