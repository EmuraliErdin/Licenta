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

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context context;
    private List<Employee> employeeList;
    private OnNoteListener onNoteListener;


    public EmployeeAdapter(Context context, List<Employee> employeeList, OnNoteListener onNoteListener) {
        this.employeeList = employeeList;
        this.context = context;
        this.onNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_employee_row,parent, false);
        return new EmployeeAdapter.EmployeeViewHolder(view,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.tvEmail.setText("Email: "+employee.getEmail());
        holder.tvName.setText("Name: "+employee.getFirstName()+" "+employee.getLastName());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView tvName;
        TextView tvEmail;
        OnNoteListener onNoteListener;

        public EmployeeViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmployeeListEmail);
            tvName = itemView.findViewById(R.id.tvEmployeeListName);
            this.onNoteListener = onNoteListener;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onNoteListener.onEmployeeClick(getAdapterPosition());
            return true;
        }
    }
    public interface OnNoteListener{
        void onEmployeeClick(int position);
    }
}
