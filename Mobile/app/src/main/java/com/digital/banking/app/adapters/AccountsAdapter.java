package com.digital.banking.app.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import com.digital.banking.app.R;
import com.digital.banking.app.pages.Operations;
import com.digital.banking.app.pages.MakeOperation;
import com.digital.banking.app.adapters.models.BankAccount;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {

    private final Context context;
    private final List<BankAccount> list;

    public AccountsAdapter(Context context, List<BankAccount> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_account, parent, false);
        return new ViewHolder(context, v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position));
        holder.view.setTag(list.get(position));
        holder.make.setTag(list.get(position));
        BankAccount element = list.get(position);
        holder.accountId.setText("Id : "+element.getId());
        DecimalFormat df = new DecimalFormat("0.000");
        holder.accountBalance.setText(element.getType() + " (" + df.format(element.getBalance()) + " DHs)");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView accountId, accountBalance;
        public Button view, make;

        public ViewHolder(final Context context, View itemView) {
            super(itemView);
            accountId = itemView.findViewById(R.id.accountId);
            accountBalance = itemView.findViewById(R.id.accountBalance);
            view = itemView.findViewById(R.id.view);
            make = itemView.findViewById(R.id.perform);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BankAccount element = (BankAccount)view.getTag();
                    Intent theIntent = new Intent(context, Operations.class);
                    theIntent.putExtra("accountId", element.getId());
                    context.startActivity(theIntent);
                }
            });

            make.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BankAccount element = (BankAccount)view.getTag();
                    Intent theIntent = new Intent(context, MakeOperation.class);
                    theIntent.putExtra("accountId", element.getId());
                    context.startActivity(theIntent);
                }
            });
        }
    }
}
