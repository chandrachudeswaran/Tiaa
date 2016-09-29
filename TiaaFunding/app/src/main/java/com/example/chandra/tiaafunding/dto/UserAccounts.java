package com.example.chandra.tiaafunding.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by chandra on 9/25/2016.
 */
public class UserAccounts implements Parcelable{

    private String pin;
    private String accountnumber;
    private String typeOfAccount;
    private double balance;
    private String bankname;
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getAccountnumber() {
        return accountnumber;
    }
    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }
    public String getTypeOfAccount() {
        return typeOfAccount;
    }
    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public String getBankname() {
        return bankname;
    }
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pin);
        dest.writeString(accountnumber);
        dest.writeString(typeOfAccount);
        dest.writeString(bankname);
        dest.writeDouble(balance);


    }

    public static final Parcelable.Creator<UserAccounts> CREATOR = new Parcelable.Creator<UserAccounts>() {
        public UserAccounts createFromParcel(Parcel in) {
            return new UserAccounts(in);
        }

        public UserAccounts[] newArray(int size) {
            return new UserAccounts[size];
        }
    };

    private UserAccounts(Parcel in) {
        this.pin = in.readString();
        this.accountnumber = in.readString();
        this.typeOfAccount = in.readString();
        this.bankname = in.readString();
        this.balance = in.readDouble();

    }
    public  UserAccounts(){

    }

    public static ArrayList<UserAccounts> getListOfAccounts(String output) {
       ArrayList<UserAccounts> list = null;
        try {
            JSONArray array = new JSONArray(output);
            list = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                if(getAccount(array.getJSONObject(i))!=null){
                    list.add(getAccount(array.getJSONObject(i)));
                }
            }

        } catch (JSONException e) {
            try {
                list.add(getAccount(new JSONObject(output)));
            } catch (JSONException e1) {
                Log.e("Invalid Response",e.toString());
            }
        }

        return list;
    }

    @Override
    public String toString() {
        return "UserAccounts{" +
                "pin='" + pin + '\'' +
                ", accountnumber='" + accountnumber + '\'' +
                ", typeOfAccount='" + typeOfAccount + '\'' +
                ", balance=" + balance +
                ", bankname='" + bankname + '\'' +
                '}';
    }

    public static UserAccounts getAccount(JSONObject object){
        UserAccounts account = null;
        try {
           account = new UserAccounts();
            account.setPin(object.getString("pin"));
            account.setAccountnumber(object.getString("accountnumber"));
            account.setTypeOfAccount(object.getString("typeOfAccount"));
            account.setBalance(object.getDouble("balance"));
            account.setBankname(object.getString("bankname"));
        } catch (JSONException e) {
            Log.e("JsonparsingError",e.toString());
        }
return account;
    }

}
