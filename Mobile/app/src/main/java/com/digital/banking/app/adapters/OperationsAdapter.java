package com.digital.banking.app.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.banking.app.R;
import com.digital.banking.app.adapters.models.AccountOperation;

import java.text.DecimalFormat;
import java.util.List;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.ViewHolder> {

    private final Context context;
    private final String accountId;
    private final List<AccountOperation> list;

    public OperationsAdapter(Context context, List<AccountOperation> list, String accountId) {
        this.context = context;
        this.list = list;
        this.accountId = accountId;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_operation, parent, false);
        return new ViewHolder(context, v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position));
        AccountOperation element = list.get(position);
        holder.title.setText(element.getOperationDate().toString().split("GMT")[0]);
        DecimalFormat df = new DecimalFormat("0.00");
        holder.description.setText(element.getType().toString());
        holder.balance.setText(df.format(element.getAmount()) + " DHs");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, balance;

        public ViewHolder(final Context context, View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            balance = itemView.findViewById(R.id.balance);
        }

    }
}
