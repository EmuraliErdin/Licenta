package com.example.licenta.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.Log;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder>{

    private List<Log> logList;
    private Context context;

    public LogAdapter(List<Log> logList, Context context){
        this.logList =logList;
        this.context=context;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_log_row,parent, false);
        return new LogAdapter.LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        Log log = logList.get(position);
        holder.tvAction.setText(log.getAction());
        holder.tvDate.setText("Date: "+log.getCreateDate());
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    class LogViewHolder extends RecyclerView.ViewHolder{

        TextView tvAction;
        TextView tvDate;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAction = itemView.findViewById(R.id.tvLogAction);
            tvDate = itemView.findViewById(R.id.tvLogDate);
        }
    }
}
