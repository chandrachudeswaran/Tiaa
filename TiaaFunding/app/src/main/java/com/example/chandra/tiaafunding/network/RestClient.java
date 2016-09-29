package com.example.chandra.tiaafunding.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by chandra on 9/25/2016.
 */


public class RestClient extends AsyncTask<RequestParams, Void, String> {
    Context context;
    String function;
    ProgressDialog dialog;
    TransferToActivity transferToActivity;

    public interface TransferToActivity {
        void doAction(String output, String function);
    }


    public RestClient(Context context, String function) {
        this.context = context;
        this.function = function;
        this.transferToActivity = (TransferToActivity) context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Requesting..");
        dialog.show();
    }

    @Override
    protected String doInBackground(RequestParams... params) {
        BufferedReader reader = null;
        String line = "";
        String status = "";
        try {
            HttpURLConnection con = params[0].getConnection();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            if ((line = reader.readLine()) != null) {
                status = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    protected void onPostExecute(String output) {
        Log.d("Output from RestCall" ,output);
        dialog.dismiss();
        transferToActivity.doAction(output, RestClient.this.function);
    }
}
