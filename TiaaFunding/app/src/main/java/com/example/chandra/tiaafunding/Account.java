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
    ArrayList<Account> achSetupAccounts;

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
        achSetupAccounts = new ArrayList<>();
        listOfAccount.add(new Account("A5734567",4567.23,"Retirement"));
        listOfAccount.add(new Account("A581234",6000,"Personal Portfolio"));
        listOfAccount.add(new Account("A5178945",23456,"Pension"));

        achSetupAccounts.add(new Account("XXX3456",0,"Bank of America Checking"));
        achSetupAccounts.add(new Account("XXX1234",0,"Bank of America Savings"));
        achSetupAccounts.add(new Account("XX156",0,"Chase Checking"));

    }

    public ArrayList<Account> getListOfAccount() {
        return listOfAccount;
    }

    public ArrayList<Account> getAchSetupAccounts() {
        return achSetupAccounts;
    }
}
