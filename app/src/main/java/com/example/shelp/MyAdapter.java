package com.example.shelp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList name_id, number_id;
    private OnEntryDeleteListener DelListener;

    public interface OnEntryDeleteListener{
        void OnEntryDelete(String name);
    }

    public void setOnEntryDeleteListener(OnEntryDeleteListener clickListener){
        DelListener = clickListener;
    }

    public MyAdapter(Context context, ArrayList name_id, ArrayList number_id) {
        this.context = context;
        this.name_id = name_id;
        this.number_id = number_id;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.contact_entry, parent, false);
        return new MyViewHolder(v, DelListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.number_id.setText(String.valueOf(number_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_id, number_id;
        ImageButton delete;
        public MyViewHolder(@NonNull View itemView, OnEntryDeleteListener DelListener) {
            super(itemView);
            name_id = itemView.findViewById(R.id.text_name);
            number_id = itemView.findViewById(R.id.text_number);
            delete = itemView.findViewById(R.id.entry_delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DelListener.OnEntryDelete(name_id.getText().toString());
                }
            });
        }
    }
}
