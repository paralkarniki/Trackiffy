package com.example.matt.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Remind extends Activity {

    private EditText edtRemind;
    private Button btnRemind;
    private CalendarView calendarView;
    private SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);

        final Calendar calendar = Calendar.getInstance();
        edtRemind = findViewById(R.id.edt_remind_Date);
        btnRemind = findViewById(R.id.btn_remind);
        dateFormat = new SimpleDateFormat("dd/MM/yy");
        final DatePickerDialog.OnDateSetListener datepick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };
        //when button remind is clicked the user is given a choice as to what date they want to be reminded
        //users are provided with a window pop up to guide them on their next step in the application
        btnRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View remind = inflater.inflate(R.layout.linear_layout_remind, null);
                final PopupWindow popupWindow = new PopupWindow(remind, 800, 1200);
                popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);

                Button btnYes = remind.findViewById(R.id.btn_yes);

                calendarView = remind.findViewById(R.id.clv_remind);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = dateFormat.format(new Date(calendarView.getDate()));
                        String notes = edtRemind.getText().toString();
                        Toast.makeText(getApplicationContext(), " Date selected is " + date + " \n " +
                                " " + notes, Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                    }
                });
            }
        });
        edtRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLabel();
            }
        });
    }

    private void updateLabel() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String date = dateFormat.format(new Date(calendarView.getDate()));
    }
}

