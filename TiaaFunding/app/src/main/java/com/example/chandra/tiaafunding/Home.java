package com.example.chandra.tiaafunding;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chandra.tiaafunding.dto.UserAccounts;
import com.example.chandra.tiaafunding.dto.UserInfo;
import com.example.chandra.tiaafunding.network.RequestParams;
import com.example.chandra.tiaafunding.network.RestClient;
import com.example.chandra.tiaafunding.util.SharedPreferenceHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Home extends AppCompatActivity implements TextToSpeech.OnInitListener, RestClient.TransferToActivity{

    ListView listView;
    ListAdapter adapter;
    TextView balance;
    private Toolbar mToolbar;
    public TextToSpeech mTts;
    UserInfo info;
    String function;
    ArrayList<UserAccounts>list;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int MY_DATA_CHECK_CODE= 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.tiaa);
        setTitle("  My Accounts");
        balance = (TextView)findViewById(R.id.balance);
        listView = (ListView)findViewById(R.id.list);

        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getParcelable("Info")!=null){
              info  = getIntent().getExtras().getParcelable("Info");
                SharedPreferenceHelper.putSharedPreferences(Home.this, info);
            }
        }

        Toast.makeText(Home.this,"Hello Mr."+ info.getLastname(),Toast.LENGTH_SHORT).show();
        retrieveAccounts(info.getPin());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.pic){
            promptVoiceInput();
        }

        if (id == R.id.fundNow) {
            Intent intent  = new Intent(Home.this,FundActivity.class);
            intent.putExtra("Type","Touch");
            intent.putParcelableArrayListExtra("Account", list);
            startActivityForResult(intent, 200);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void promptVoiceInput(){
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
    }

    public void retrieveAccounts(String pin){
        RequestParams params = new RequestParams(AppConstants.baseurl, "POST");
        this.function = AppConstants.FUNCTION_GETACCOUNTS;
        params.setUrl(this.function);
        params.addParams("pin", pin);
        new RestClient(Home.this,AppConstants.FUNCTION_SESSION).execute(params);
    }

    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

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

                    String converted_text = (result.get(0));
                    Log.d("Converted Text: ", converted_text);
                    converted_text = converted_text.toLowerCase();
                    converted_text = converted_text.replace("what do you want to do","");

                    if(converted_text.contains("transfer") || converted_text.contains("fund") ||
                            converted_text.contains("money")){
                        Intent intent  = new Intent(Home.this,FundActivity.class);
                        intent.putExtra("Type","Voice");
                        intent.putParcelableArrayListExtra("Account",list);
                        startActivityForResult(intent, 2000);
                    }

                }
                break;
            }

            case MY_DATA_CHECK_CODE: {
                if (requestCode == MY_DATA_CHECK_CODE) {
                    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                        mTts = new TextToSpeech(Home.this, Home.this);

                    } else {
                        Intent installIntent = new Intent();
                        installIntent.setAction(
                                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(installIntent);
                    }
                }
                break;
            }

            case 2000:
                if(mTts!=null){
                    mTts.shutdown();
                }


        }
    }



    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTts.setLanguage(Locale.US);
            HashMap<String, String> params = new HashMap<>();
            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "stringId");
            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
            String greeting= "Good Morning";
            if (timeOfDay >= 0 && timeOfDay < 12) {
                greeting= "Good Morning";
            } else if (timeOfDay >= 12 && timeOfDay < 16) {
                greeting= "Good Afternoon";
            } else if (timeOfDay >= 16 && timeOfDay < 21) {
                greeting = "Good Evening";
            } else if (timeOfDay >= 21 && timeOfDay < 24) {
                greeting = "Good Night";
            }
            mTts.speak(greeting+ " Mr. " + info.getLastname()+ " How can I help you today", TextToSpeech.QUEUE_FLUSH, params);
            mTts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                @Override
                public void onUtteranceCompleted(String utteranceId) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            promptSpeechInput();
                        }
                    });
                }
            });

        }else {
            Log.d("error", "error");
        }
    }

    @Override
    public void doAction(String output, String function) {
        ArrayList<UserAccounts> accounts = UserAccounts.getListOfAccounts(output);
        list= accounts;
        if(accounts!=null) {

            adapter = new ListAdapter(Home.this,R.layout.listrow,accounts);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
            double bal=0.0;
            for(UserAccounts acc:accounts){
                bal = bal+ acc.getBalance();
            }
            DecimalFormat df2 = new DecimalFormat("###.##");
            bal = Double.valueOf(df2.format(bal));
            balance.setText("$ " + bal);
        }
    }
}
