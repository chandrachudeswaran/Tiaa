package com.example.chandra.tiaafunding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    TextView from;
    TextView to;
    TextView amount;
    TextView date;
    TextView confirmation;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
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
        Random rand = new Random();
        confirmation.setText("C"+rand.nextInt(5000) + 1000);
        status.setText("Your Transfer was Successful");
    }



    public void showHome(View view) {
        doShowHome();
    }

    public void doShowHome(){
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        doShowHome();
    }
}
