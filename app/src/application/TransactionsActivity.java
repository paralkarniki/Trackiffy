package com.example.matt.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.matt.myapplication.db.TransactionDb;
import com.example.matt.myapplication.model.Transaction;

public class TransactionsActivity extends Activity {

    private RecyclerView rcvTransactions;
    private TransactionAdapter adapter;
    private TransactionDb transDb;

    private static final int ADD_TRANSACTION = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        transDb = new TransactionDb(getApplicationContext());
        loadTransactions();

        findViewById(R.id.btn_add_trans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTransActivity.class);
                startActivityForResult(intent, ADD_TRANSACTION);
            }
        });

        //This button will launch a popup window to confirm that the user does in fact
        //wish to clear the database, in case of accidental tapping. Selecting yes clears
        //the database, selecting no simply closes the popup window.

        findViewById(R.id.btn_clear_trans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Referenced this page for information on how to properly start the PopupWindow
                //https://android--code.blogspot.com/2016/01/android-popup-window-example.html

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                View customView = inflater.inflate(R.layout.linear_layout_popup, null);

                final PopupWindow popupWindow = new PopupWindow(customView,
                        800, 400);

                popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);

                Button btnYes = customView.findViewById(R.id.btn_clear_yes);
                Button btnNo = customView.findViewById(R.id.btn_clear_no);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transDb.deleteAllTransactions();
                        adapter = new TransactionAdapter(transDb.getAllTransactions());
                        rcvTransactions.setAdapter(adapter);
                        popupWindow.dismiss();
                    }
                });

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });

    }

    //initially sets up the RecyclerView
    private void loadTransactions() {
        rcvTransactions = findViewById(R.id.rcv_transactions);

        rcvTransactions.setHasFixedSize(true);

        rcvTransactions.addItemDecoration(new DividerItemDecoration(rcvTransactions.getContext(), DividerItemDecoration.VERTICAL));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rcvTransactions.setLayoutManager(manager);

        adapter = new TransactionAdapter(transDb.getAllTransactions());
        rcvTransactions.setAdapter(adapter);

    }

    //adds a transaction received from the AddTransActivity to the database
    //and to the RecyclerView
    private void appendTransaction(Transaction transaction) {
        transDb.saveTransaction(transaction);
        adapter = new TransactionAdapter(transDb.getAllTransactions());
        rcvTransactions.setAdapter(adapter);

    }

    //Receives the parcelable transaction from the AddTransActivity and sends
    //it to the appendTransaction method above
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_TRANSACTION) {
            if (resultCode == RESULT_OK) {

                Transaction transaction = data.getParcelableExtra("transaction");

                appendTransaction(transaction);

            }
        }

    }

    //Menu button fo the user to reverte back and forth to the setting and reminder page
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    //allows user to select between settings page and reminder page
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == 0) {
            Intent intent1 = new Intent(getApplicationContext(),
                    UserPrefActivity.class);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(getApplicationContext(),
                    Remind.class);
            startActivity(intent2);
        }

        return super.onOptionsItemSelected(item);

    }
}
