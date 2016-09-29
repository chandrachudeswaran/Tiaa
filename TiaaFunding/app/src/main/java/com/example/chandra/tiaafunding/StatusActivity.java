package com.example.chandra.tiaafunding;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chandra.tiaafunding.dto.UserInfo;
import com.example.chandra.tiaafunding.network.MailClient;
import com.example.chandra.tiaafunding.network.RequestParams;
import com.example.chandra.tiaafunding.network.RestClient;
import com.example.chandra.tiaafunding.util.SharedPreferenceHelper;


import org.apache.commons.lang3.text.StrBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class StatusActivity extends AppCompatActivity implements TextToSpeech.OnInitListener,RestClient.TransferToActivity{

    private Toolbar mToolbar;
    TextView from;
    TextView to;
    TextView amount;
    TextView date;
    TextView confirmation;
    TextView status;
    String voiceText;
    public TextToSpeech mTts;
    String function;
    private final int MY_DATA_CHECK_CODE= 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.tiaa);
        setTitle("  Transfer Status");
        from = (TextView)findViewById(R.id.fromAccount);
        to =(TextView)findViewById(R.id.toAccount);
        amount = (TextView)findViewById(R.id.amount);
        date= (TextView)findViewById(R.id.date);
        confirmation = (TextView)findViewById(R.id.confirmation);
        status = (TextView)findViewById(R.id.status);
        displayConfirmation();
    }

    public void displayConfirmation(){
        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getString("from")!=null){
                from.setText(getIntent().getExtras().getString("from"));
            }
            if(getIntent().getExtras().getString("to")!=null){
                to.setText(getIntent().getExtras().getString("to"));
            }
            if(getIntent().getExtras().getString("amount")!=null){
                amount.setText(getIntent().getExtras().getString("amount"));
            }
            if(getIntent().getExtras().getString("date")!=null){
                date.setText(getIntent().getExtras().getString("date"));
            }
        }

        Date date1 = new Date();
        confirmation.setText("C"+date1.getTime());
        status.setText("Your Transfer was Successful");

        try {

            String ph=SharedPreferenceHelper.loadSavedPreferences(StatusActivity.this).getPhonenumber();
            StrBuilder builder = new StrBuilder();
            builder.append("Dear " + " " + SharedPreferenceHelper.loadSavedPreferences(StatusActivity.this).getFirstname() + " , ");
            builder.appendln(" You have received the following transfer :");
            builder.appendln("From: " + from.getText().toString());
            builder.appendln("");

            builder.appendln("To: " + to.getText().toString());
            builder.appendln("");
            builder.appendln("Amount: " + amount.getText().toString());
            builder.appendln("");
            builder.appendln("Transaction Date: " + date.getText().toString());
            builder.appendln("");
            builder.appendln("Confirmation: " + confirmation.getText().toString());

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(ph, "9802678225",builder.toString(), null, null);
            Toast.makeText(StatusActivity.this, "Message Sent",
                    Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(StatusActivity.this, "Message not Sent",
                    Toast.LENGTH_LONG).show();
        }

        RequestParams params = new RequestParams(AppConstants.baseurl, "POST");
        this.function = AppConstants.FUNCTION_SUBMITFUNDING;
        params.setUrl(this.function);
        params.addParams("orchestrationid", confirmation.getText().toString());
        params.addParams("pin", SharedPreferenceHelper.loadSavedPreferences(StatusActivity.this).getPin());
        params.addParams("fromaccount", from.getText().toString());
        String[] temp = to.getText().toString().split("-");
        params.addParams("toaccount", temp[1].trim());
        params.addParams("amount",amount.getText().toString().replace("$","").replace(",","").trim());
        params.addParams("date",date.getText().toString());
        params.addParams("balance",String.valueOf(SharedPreferenceHelper.loadSavedPreferencesForBalance(StatusActivity.this)));
        new RestClient(StatusActivity.this,AppConstants.FUNCTION_SUBMITFUNDING).execute(params);

        new MailClient(StatusActivity.this).execute(from.getText().toString(), to.getText().toString(),
               amount.getText().toString(), date.getText().toString(), confirmation.getText().toString());

        if(getIntent().getExtras().getString("Type") !=null){
            if("Voice".equalsIgnoreCase(getIntent().getExtras().getString("Type"))){
                promptVoiceInput();
            }
        }

    }

    @Override
    public void doAction(String output, String function) {

    }

    public void showHome(View view) {
        doShowHome();
    }

    public void doShowHome(){
        if(mTts!=null){
            mTts.shutdown();
        }
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        doShowHome();
    }

    private void promptVoiceInput(){
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        StringBuilder sb = new StringBuilder();
        sb.append("Your funds will be transferred within two business dates and an email has been sent for your confirmation");
        voiceText = sb.toString();
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case MY_DATA_CHECK_CODE:{
                if (requestCode == MY_DATA_CHECK_CODE) {
                    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                        mTts = new TextToSpeech(StatusActivity.this, StatusActivity.this);
                    } else {
                        Intent installIntent = new Intent();
                        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(installIntent);
                    }
                }
                break;
            }

        }
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
                            //promptSpeechInput(voiceText);
                        }
                    });
                }
            });

        }else {
            Log.d("error", "error");
        }
    }



}
