package com.example.matt.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matt.myapplication.model.Transaction;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private ArrayList<Transaction> mTransactions;

    public TransactionAdapter(ArrayList<Transaction> transactions) {

        mTransactions = transactions;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtItem;
        private TextView txtCost;
        private TextView txtDate;
        private TextView txtSeller;
        private TextView txtPayment;

        public MyViewHolder(LinearLayout layout) {
            super(layout);
            txtItem = layout.findViewById(R.id.txt_item);
            txtCost = layout.findViewById(R.id.txt_cost);
            txtDate = layout.findViewById(R.id.txt_date);
            txtSeller = layout.findViewById(R.id.txt_seller);
            txtPayment = layout.findViewById(R.id.txt_payment);

        }
    }

    @Override
    public TransactionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate
                (R.layout.linear_layout_transactions, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(layout);

        return viewHolder;
    }


    //reads the transactions and adds them to the RecyclerView
    @Override
    public void onBindViewHolder(TransactionAdapter.MyViewHolder myViewHolder, int position) {

        myViewHolder.txtItem.setText(mTransactions.get(position).getItem());
        myViewHolder.txtCost.setText(mTransactions.get(position).getCost());
        myViewHolder.txtDate.setText(mTransactions.get(position).getDate());
        myViewHolder.txtSeller.setText(mTransactions.get(position).getSeller());
        myViewHolder.txtPayment.setText(mTransactions.get(position).getPayment());

    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }
}
