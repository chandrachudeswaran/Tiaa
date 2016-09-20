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

   // private final int REQ_CODE_SPEECH_INPUT = 100;

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
   /* private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // here the string converted from your voice
                    String converted_text = (result.get(0));
                    Log.d("f",converted_text);
                    converted_text = converted_text.toLowerCase();
                    if(converted_text.contains("balance") || converted_text.contains("accounts")){
                        Intent intent = new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                    }
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pic) {
            promptSpeechInput();
        }
        return super.onOptionsItemSelected(item);
    }*/


}
