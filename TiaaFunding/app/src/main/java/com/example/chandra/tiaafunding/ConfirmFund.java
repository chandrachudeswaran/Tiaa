package com.example.chandra.tiaafunding;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ConfirmFund extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextView from;
    TextView to;
    TextView amount;
    TextView date;
    private Toolbar mToolbar;
    public TextToSpeech mTts;
    private final int MY_DATA_CHECK_CODE= 1200;
    String voiceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_fund);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.tiaa);
        setTitle("  Confirm Transfer");
        from = (TextView)findViewById(R.id.fromAccount);
        to =(TextView)findViewById(R.id.toAccount);
        amount = (TextView)findViewById(R.id.amount);
        date= (TextView)findViewById(R.id.date);

        displayConfirmation();

    }

    public void displayConfirmation(){
        if(getIntent().getExtras().getString("from")!=null){
            from.setText(getIntent().getExtras().getString("from"));
        }
        if(getIntent().getExtras().getString("to")!=null){
            to.setText(getIntent().getExtras().getString("to"));
        }
        if(getIntent().getExtras().getString("amount")!=null){
            amount.setText("$ "+ getIntent().getExtras().getString("amount"));
        }
        if(getIntent().getExtras().getString("date")!=null){
            date.setText(getIntent().getExtras().getString("date"));
        }

        if(getIntent().getExtras().getString("Type") !=null){
            if("Voice".equalsIgnoreCase(getIntent().getExtras().getString("Type"))){
                promptVoiceInput();
            }
        }
    }


    public void doEdit(View view) {
        goBack();
    }

    public void goBack(){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
    public void initiateTransfer(){
        Intent intent = new Intent(ConfirmFund.this, StatusActivity.class);
        intent.putExtra("from", from.getText().toString());
        intent.putExtra("to", to.getText().toString());
        intent.putExtra("amount", amount.getText().toString());
        intent.putExtra("date", date.getText().toString());
        startActivityForResult(intent, 2000);
    }

    public void doTransfer(View view) {
        initiateTransfer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case MY_DATA_CHECK_CODE:{
                if (requestCode == MY_DATA_CHECK_CODE) {
                    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                        mTts = new TextToSpeech(ConfirmFund.this, ConfirmFund.this);
                    } else {
                        Intent installIntent = new Intent();
                        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(installIntent);
                    }
                }
                break;
            }
            case 2000: {
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            }

            case 3000:{
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String converted_text = (result.get(0));
                    Log.d("Voice Input Text: ", converted_text);
                    converted_text = converted_text.toLowerCase();
                    if(converted_text.contains("yes") || converted_text.contains("confirm")|| converted_text.contains("sure") || converted_text.contains("go ahead")){
                        initiateTransfer();
                    }else{
                        goBack();
                    }
                }
                break;
            }

        }
    }

    private void promptVoiceInput(){
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        StringBuilder sb = new StringBuilder();
        sb.append("Please confirm whether you want to transfer ");
        sb.append(" "+amount.getText().toString());
        sb.append(" from your ");
        sb.append(from.getText().toString() + " account ");
        sb.append(" to your "+ to.getText().toString() + " account today");
        voiceText = sb.toString();
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTts.setLanguage(Locale.US);
            HashMap<String, String> params = new HashMap<>();
            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "stringId");
            mTts.speak(voiceText, TextToSpeech.QUEUE_FLUSH, params);
            mTts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                @Override
                public void onUtteranceCompleted(String utteranceId) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            promptSpeechInput(voiceText);
                        }
                    });
                }
            });

        }else {
            Log.d("error", "error");
        }
    }

    private void promptSpeechInput(String text) {
        if (text == null) {
            text = "Say Something";
        }
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, text);


        try {
            startActivityForResult(intent,3000);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }
}
