package com.example.chandra.tiaafunding;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ConfirmFund extends AppCompatActivity {

    TextView from;
    TextView to;
    TextView amount;
    TextView date;
    private Toolbar mToolbar;
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
    }


    public void doEdit(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    public void doTransfer(View view) {

        Intent intent = new Intent(ConfirmFund.this, StatusActivity.class);
        intent.putExtra("from", from.getText().toString());
        intent.putExtra("to", to.getText().toString());
        intent.putExtra("amount", amount.getText().toString());
        intent.putExtra("date", date.getText().toString());
        startActivityForResult(intent, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 2000: {
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            }

        }
    }
}
