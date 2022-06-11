package com.example.licenta.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.classes.Issue;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder>{
    private Context context;
    private List<Issue> issueList;
    private IssueAdapter.OnNoteListener onNoteListener;

    public IssueAdapter(Context context, List<Issue> issueList, IssueAdapter.OnNoteListener onNoteListener) {
        this.issueList = issueList;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public IssueAdapter.IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_issue_row,parent, false);
        return new IssueAdapter.IssueViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueAdapter.IssueViewHolder holder, int position) {
        Issue issue = issueList.get(position);
        holder.tvPriorityLevel.setText("Priority level: "+issue.getPriorityLevel());
        holder.tvStatus.setText("Status: "+issue.getStatus());
        holder.tvReason.setText("Reason: "+issue.getReason());
        holder.tvCreateDate.setText("Created on: "+issue.getCreateDate());
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView tvPriorityLevel;
        TextView tvStatus;
        TextView tvReason;
        TextView tvCreateDate;
        IssueAdapter.OnNoteListener onNoteListener;

        public IssueViewHolder(@NonNull View itemView, IssueAdapter.OnNoteListener onNoteListener) {
            super(itemView);
            tvPriorityLevel = itemView.findViewById(R.id.tvPriorityLevel);
            tvStatus = itemView.findViewById(R.id.tvIssueStatus);
            tvReason = itemView.findViewById(R.id.tvIssueReason);
            tvCreateDate = itemView.findViewById(R.id.tvIssueCreateDate);
            this.onNoteListener = onNoteListener;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onNoteListener.onIssueClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnNoteListener{
        void onIssueClick(int position);
    }
}
