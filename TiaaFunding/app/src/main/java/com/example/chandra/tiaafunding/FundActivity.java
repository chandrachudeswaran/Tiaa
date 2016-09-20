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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

public class FundActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    Spinner fromSpinner;
    Spinner toSpinner;
    TextView date;
    EditText amount;
    List<String> fromList;
    List<String> toList;
    LinkedHashSet<String> fromSet;
    LinkedHashSet<String> toSet;
    private Toolbar mToolbar;
    ImageView picker;
    LinearLayout parent;
    TextView toBalance;
    public TextToSpeech mTts;
    public String voiceText;
    private final int MY_DATA_CHECK_CODE= 1200;



    public int activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund);
        activity=0;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.tiaa);
        setTitle("  Transfer Money");

        date = (TextView) findViewById(R.id.date);
        amount = (EditText) findViewById(R.id.amount);
        picker =(ImageView)findViewById(R.id.picker);
        parent = (LinearLayout)findViewById(R.id.calendar);
        toBalance = (TextView)findViewById(R.id.toBalance);

        Date cdate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date.setText(dateFormat.format(cdate));
        addFromAccount();
        addToAccount();

        if(getIntent().getExtras()!=null){
            if (getIntent().getExtras().getString("Type") != null) {
                if ("Voice".equalsIgnoreCase(getIntent().getExtras().getString("Type"))) {
                    activity=100;
                    promptVoiceInput(AppConstants.fromText);

                }
            }
        }





    }


    public void addFromAccount() {
        fromSpinner = (Spinner) findViewById(R.id.fromAccount);
        fromList = new ArrayList<>();
        fromList.add("Bank of America - Checking XXX3456");
        fromList.add("Bank of America - Savings XXX1234");
        fromList.add("Chase - Savings XX156");
        fromSet = new LinkedHashSet<>();
        fromSet.add("Bank of America Checking");
        fromSet.add("Bank of America Savings");
        fromSet.add("Chase");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(dataAdapter);
    }

    public void addToAccount() {
        toSpinner = (Spinner) findViewById(R.id.toAccount);
        toList = new ArrayList<>();
        toList.add("Personal Portfolio - A581234");
        toSet = new LinkedHashSet<>();
        toSet.add("Personal Portfolio");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_fund, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pic) {
            promptSpeechInput(null);
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void doCancel(View view) {
        finish();
    }

    public void proceedConfirmation(View view) {
        callConfirmationPage();

    }

    public void callConfirmationPage() {
        if(validateAmount()){
            Intent intent = new Intent(FundActivity.this, ConfirmFund.class);
            intent.putExtra("from", fromSpinner.getSelectedItem().toString());
            intent.putExtra("to", toSpinner.getSelectedItem().toString());
            intent.putExtra("amount", amount.getText().toString());
            intent.putExtra("date", date.getText().toString());
            startActivityForResult(intent, 2000);
        }
    }

    private boolean validateAmount(){
        if(amount.getText().length()==0){
            Toast.makeText(getApplicationContext(),"Enter Valid transfer amount",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
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
            startActivityForResult(intent, activity);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String converted_text = (result.get(0));
                    Log.d("Voice Input Text: ", converted_text);

                    int j=0;
                    for(String account:fromSet){
                        if(account.contains(converted_text)){
                            j++;
                        }
                    }
                    if(j>1){
                        StringBuilder sb = new StringBuilder();
                        sb.append("There are two ");
                        sb.append(converted_text);
                        sb.append(" accounts. Which one to select");
                        activity=100;
                        promptVoiceInput(sb.toString());
                    }else {
                        int i = 0;
                        for (String account : fromSet) {
                            account = account.toLowerCase();
                            converted_text = converted_text.toLowerCase();
                            if (account.contains(converted_text)) {
                                fromSpinner.setSelection(i);
                                break;
                            } else {
                                i++;
                            }
                        }
                        activity=200;
                        promptVoiceInput(AppConstants.toText);
                    }
                }
                break;
            }

            case 200: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String converted_text = (result.get(0));
                    Log.d("Voice Input Text: ", converted_text);
                    int i = 0;
                    for (String account : toSet) {
                        if (account.contains(converted_text)) {
                            toSpinner.setSelection(i);
                            break;
                        } else {
                            i++;
                        }
                    }
                    activity=300;
                    promptVoiceInput(AppConstants.amountText);

                }
                break;
            }

            case 300: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String converted_text = (result.get(0));
                    Log.d("Voice Input Text: ", converted_text);
                    converted_text = converted_text.replace("$", "");
                    amount.setText(converted_text);
                    callConfirmationPage();
                }
                break;
            }

            case 400: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String converted_text = (result.get(0));
                    Log.d("Voice Input Text: ", converted_text);
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

            case MY_DATA_CHECK_CODE: {
                if (requestCode == MY_DATA_CHECK_CODE) {
                    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                        mTts = new TextToSpeech(FundActivity.this, FundActivity.this);

                    } else {
                        Intent installIntent = new Intent();
                        installIntent.setAction(
                                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(installIntent);
                    }
                }
            }
        }
    }

    public void showCalendar(View view) {

        final CalendarView calendarView = new CalendarView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        calendarView.setLayoutParams(layoutParams);
        parent.addView(calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                parent.removeView(calendarView);
                month = month + 1;
                date.setText(month + "/" + dayOfMonth + "/" + year);
            }
        });
    }

    private void promptVoiceInput(String text){
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        voiceText = text;
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
}
