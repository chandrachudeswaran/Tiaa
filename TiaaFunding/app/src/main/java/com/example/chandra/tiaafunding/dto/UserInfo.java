package com.example.chandra.tiaafunding.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chandra on 9/25/2016.
 */
public class UserInfo implements Parcelable {


    public UserInfo(){

    }

    private String pin;
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pin);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(email);
        dest.writeString(phonenumber);


    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    private UserInfo(Parcel in) {
        this.pin = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.email = in.readString();
        this.phonenumber = in.readString();

    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "pin='" + pin + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }

    public static UserInfo getUserInfoObject(String output){
        UserInfo info = new UserInfo();
        try {
            JSONObject obj = new JSONObject(output);
            info.setPin(obj.getString("pin"));
            info.setFirstname(obj.getString("firstname"));
            info.setLastname(obj.getString("lastname"));
            info.setEmail(obj.getString("email"));
            info.setPhonenumber(obj.getString("phonenumber"));
        } catch (JSONException e) {
            return null;
        }

        Log.d("Userinfo",info.toString());
        return info;
    }



}
