package com.example.chandra.tiaafunding.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.chandra.tiaafunding.util.EmailHelper;
import com.example.chandra.tiaafunding.util.SharedPreferenceHelper;

import org.apache.commons.lang3.text.StrBuilder;

import javax.mail.MessagingException;

/**
 * Created by chandra on 9/25/2016.
 */
public class MailClient extends AsyncTask<String,Void,String> {

    Context context;
    public MailClient(Context context){
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Email","Email sent");
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            EmailHelper helper = new EmailHelper();
            Log.d("Gmail", SharedPreferenceHelper.loadSavedPreferences(context).getEmail());
            String subject = "Tiaa Transfer Alert: Transfer received for your account";
            StrBuilder builder = new StrBuilder();
            builder.append("Dear " + " "+ SharedPreferenceHelper.loadSavedPreferences(context).getFirstname()+ " , ");
            builder.appendln(" You have received the following transfer :");
            builder.appendln("From: " + params[0]);
            builder.appendln("");
            builder.appendln("To: " + params[1]);
            builder.appendln("");
            builder.appendln("Amount: " + params[2]);
            builder.appendln("");
            builder.appendln("Transaction Date: " + params[3]);
            builder.appendln("");
            builder.appendln("Confirmation: " + params[4]);
            helper.sendEmailMessage(SharedPreferenceHelper.loadSavedPreferences(context).getEmail(), subject, builder.toString());
        } catch (MessagingException e) {
            Log.e("Email",e.toString());
        }
        return null;
    }
}
