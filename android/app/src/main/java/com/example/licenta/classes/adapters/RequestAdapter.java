package com.example.licenta.classes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.classes.Request;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private List<Request> requestList;
    private Context context;
    private List<String> nameList;
    private OnRequestListener onRequestListener;

    public  RequestAdapter(Context context, List<Request> requestList, List<String> nameList, OnRequestListener onRequestListener)
    {
        this.context = context;
        this.requestList = requestList;
        this.nameList = nameList;
        this.onRequestListener = onRequestListener;
    }


    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_requests_row,parent, false);
        return new RequestViewHolder(view, onRequestListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Request request = requestList.get(position);
        String name = nameList.get(position);
        holder.tvRequestType.setText("Type: "+request.getType());
        holder.tvRequestDate.setText("Requested date: "+request.getRequestDate().toString());
        holder.tvNumberOfHours.setText("Number of hours requested:"+request.getNumberOfHours());
        holder.tvCreateDate.setText("Created on: "+request.getCreateDate().toString());
        holder.tvReason.setText("Reason: "+request.getReason());
        holder.tvStatus.setText("Status: "+request.getStatus());
        holder.tvName.setText("Name: "+name);

        switch (request.getStatus()) {
            case "PENDING":
                holder.imageview.setColorFilter(ContextCompat.getColor(context, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case "ACCEPTED":
                holder.imageview.setColorFilter(ContextCompat.getColor(context, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                    break;
            case "REFUSED":
                holder.imageview.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class RequestViewHolder extends  RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView tvStatus;
        TextView tvReason;
        TextView tvCreateDate;
        TextView tvRequestDate;
        TextView tvNumberOfHours;
        TextView tvRequestType;
        TextView tvName;
        ImageView imageview;
        OnRequestListener onRequestListener;

        public RequestViewHolder(@NonNull View itemView, OnRequestListener onRequestListener) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvListStatus);
            tvCreateDate = itemView.findViewById(R.id.tvListCreateDate);
            tvReason = itemView.findViewById(R.id.tvListReason);
            tvRequestDate = itemView.findViewById(R.id.tvListRequestedDate);
            tvNumberOfHours = itemView.findViewById(R.id.tvListNumberOfHours);
            tvRequestType = itemView.findViewById(R.id.tvListRequestType);
            imageview = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tvListName);
            this.onRequestListener = onRequestListener;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onRequestListener.onRequestClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnRequestListener{
        void onRequestClick(int position);
    }

}
