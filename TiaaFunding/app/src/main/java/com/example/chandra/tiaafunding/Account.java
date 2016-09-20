package com.example.chandra.tiaafunding;

import java.util.ArrayList;

/**
 * Created by chandra on 9/7/2016.
 */
public class Account {

    String accountnumber;
    double balance;
    String typeOfAccount;

    ArrayList<Account> listOfAccount;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public Account(String accountnumber,double balance,String typeOfAccount){
        this.accountnumber = accountnumber;
        this.balance = balance;
        this.typeOfAccount = typeOfAccount;
    }


    public Account(){
        listOfAccount = new ArrayList<>();
        listOfAccount.add(new Account("A5734567",4567.23,"IRA"));
        listOfAccount.add(new Account("A581234",6000,"Personal Portfolio"));
        listOfAccount.add(new Account("A5178945",23456,"Brokerage"));

    }

    public ArrayList<Account> getListOfAccount() {
        return listOfAccount;
    }


}
