package com.example.matt.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Transaction implements Parcelable {

    private String item;
    private String cost;
    private String date;
    private String seller;
    private String payment;

    //default constructor for Transaction
    public Transaction(String item, String cost, String date, String seller, String payment) {
        this.item = item;
        this.cost = cost;
        this.date = date;
        this.seller = seller;
        this.payment = payment;
    }

    //parcel constructor for Transaction
    protected Transaction(Parcel in) {
        item = in.readString();
        cost = in.readString();
        date = in.readString();
        seller = in.readString();
        payment = in.readString();
    }

    public String getItem() {return item;}

    public String getCost() {return cost;}

    public String getDate() {return date;}

    public String getSeller() {return seller;}

    public String getPayment() {return payment;}

    //methods necessary to make Transaction parcelable
    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item);
        dest.writeString(cost);
        dest.writeString(date);
        dest.writeString(seller);
        dest.writeString(payment);
    }

}
