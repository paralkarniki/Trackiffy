package com.example.matt.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matt.myapplication.db.TransactionDb;

public class MainActivity extends Activity {

    Button btnToTrans, btnLogOut;
    TransactionDb transDb;
    private TextView txtTotal;
    private TextView txtCash;
    private TextView txtCheque;
    private TextView txtDebit;
    private TextView txtCredit;
    private TextView txtWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gets the username entered on the sign-in page
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        txtWelcome = findViewById(R.id.txt_welcome);
        txtTotal = findViewById(R.id.txt_total);
        txtCash = findViewById(R.id.txt_cash);
        txtCheque = findViewById(R.id.txt_cheque);
        txtDebit = findViewById(R.id.txt_debit);
        txtCredit = findViewById(R.id.txt_credit);

        txtWelcome.setText("Welcome, " + username);

        btnToTrans = findViewById(R.id.btn_trans);
        btnLogOut = findViewById(R.id.btn_logout);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), Homepage.class);
                startActivity(intent2);
            }
        });

        //launches the TransactionsActivity
        btnToTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);

                startActivity(intent);
            }
        });
    }


    //each time the MainActivity is brought back to the focus of the app,
    //this updates the values for the total spending, and the spending by
    //payment method.
    @Override
    protected void onStart() {
        super.onStart();

        transDb = new TransactionDb(getApplicationContext());

        String format = "$%.2f";
        txtTotal.setText(String.format(format, transDb.getAllSpending()));

        format = "Cash: $%.2f";
        txtCash.setText(String.format(format, transDb.getSpendingByPayment("Cash")));

        format = "Cheque: $%.2f";
        txtCheque.setText(String.format(format, transDb.getSpendingByPayment("Cheque")));

        format = "Debit: $%.2f";
        txtDebit.setText(String.format(format, transDb.getSpendingByPayment("Debit")));

        format = "Credit: $%.2f";
        txtCredit.setText(String.format(format, transDb.getSpendingByPayment("Credit")));

    }
}
