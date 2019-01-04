package com.example.matt.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.matt.myapplication.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTransActivity extends Activity {

    TextView txtDisplay;
    EditText edtItem, edtSeller, edtCost;
    CalendarView clvDate;
    Spinner spnPayMethod;
    Button btnConfirmAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtransaction);

        txtDisplay = findViewById(R.id.txt_display);
        edtItem = findViewById(R.id.edt_item);
        edtSeller = findViewById(R.id.edt_seller);
        edtCost = findViewById(R.id.edt_cost);

        clvDate = findViewById(R.id.clv_date);

        spnPayMethod = findViewById(R.id.spn_pay_method);

        btnConfirmAdd = findViewById(R.id.btn_confirmAdd);


        btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean failed = false;

                //error checking for each EditText is done here

                if (edtItem.getText().toString().equals("")) {
                    edtItem.setError("Enter the good/service that you purchased");
                    failed = true;
                }

                if (edtSeller.getText().toString().equals("")) {
                    edtSeller.setError("Enter the seller of the good/service");
                    failed = true;
                }

                if (edtCost.getText().toString().equals("")) {
                    edtCost.setError("Enter the price of the good/service");
                    failed = true;
                }

                if (failed) {
                    return;
                }

                //calls createTransaction method from below to create
                //the transaction from the entered information, then
                //adds the transaction to the new intent to be sent on
                //activity result
                Transaction transaction = createTransaction();

                Intent intent = new Intent();
                intent.putExtra("transaction", transaction);

                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });

    }


    //reads the data from the EditTexts, Spinner, and CalendarView and formats
    //all of it into strings
    private Transaction createTransaction() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String item = edtItem.getText().toString();
        String cost = edtCost.getText().toString();
        String date = dateFormat.format(new Date(clvDate.getDate()));
        String seller = edtSeller.getText().toString();
        String method = spnPayMethod.getSelectedItem().toString();

        return new Transaction(item, cost, date, seller, method);

    }

    protected void onResume() {
        super.onResume();

        // get the Shared Preferences that are used by the preference screens
        SharedPreferences userPrefs =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        // first we do the CheckBox
        boolean savePassword = userPrefs.
                getBoolean(getString(R.string.preference_save_password), false);
        boolean paymentReminder = userPrefs.
                getBoolean(getString(R.string.preference_payment_reminder), false);
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
