package com.example.chandra.tiaafunding;


import android.content.Intent;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chandra.tiaafunding.dto.UserInfo;
import com.example.chandra.tiaafunding.network.RequestParams;
import com.example.chandra.tiaafunding.network.RestClient;


public class MainActivity extends AppCompatActivity implements RestClient.TransferToActivity {

    private Toolbar mToolbar;

    private EditText username_edit;
    private EditText password_edit;
    private Button login;

    String android_id;
    String function;
    boolean session;

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
        android_id = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("device",android_id);
        checkSessionExists();
    }
    public void checkSessionExists(){
        RequestParams params = new RequestParams(AppConstants.baseurl, "POST");
        this.function = AppConstants.FUNCTION_SESSION;
        params.setUrl(this.function);
        params.addParams("deviceid", android_id);
        new RestClient(MainActivity.this,AppConstants.FUNCTION_SESSION).execute(params);
    }

    public void doLogin(View view) {

        if(validateInput()){
            doLoginCheck();
        }else{
            Toast.makeText(MainActivity.this,"Please enter all details to login",Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void doAction(String output, String function) {
        UserInfo info = UserInfo.getUserInfoObject(output);
        if(info!=null) {
            session = true;
            showHome(info);
        }
    }


    public void showHome(UserInfo info){
        Intent intent = new Intent(MainActivity.this,Home.class);
        intent.putExtra("Info",info);
        startActivity(intent);
    }


    public void doLoginCheck(){
        RequestParams params = new RequestParams(AppConstants.baseurl, "POST");
        this.function = AppConstants.FUNCTION_LOGIN;
        params.setUrl(this.function);
        params.addParams("username", username_edit.getText().toString());
        params.addParams("password", password_edit.getText().toString());
        params.addParams("device", android_id);
        new RestClient(MainActivity.this,AppConstants.FUNCTION_SESSION).execute(params);
    }

    public boolean validateInput(){
        if(username_edit.getText().length()==0|| password_edit.getText().length()==0){
            return false;
        }else{
            return true;
        }
    }

}
