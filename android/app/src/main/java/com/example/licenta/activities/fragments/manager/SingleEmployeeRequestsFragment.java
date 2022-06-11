package com.example.licenta.activities.fragments.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.Request;
import com.example.licenta.classes.adapters.RequestAdapter;
import com.example.licenta.classes.converters.RequestJsonConverter;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SingleEmployeeRequestsFragment extends Fragment implements RequestAdapter.OnRequestListener {

    RecyclerView rvRequests;
    Employee employee;
    Employee manager;
    List<Request> requestList;
    Dialog dRequestDetails;

    public SingleEmployeeRequestsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        employee = (Employee) bundle.getSerializable("employee");
        manager = (Employee) bundle.getSerializable("manager");


        return inflater.inflate(R.layout.fragment_single_employee_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        rvRequests = getView().findViewById(R.id.rvSingleEmployeeFragment);
        getRequests(employee);
    }

    private void getRequests(Employee employee) {
        String url = getString(R.string.url)+"employees/"+employee.getId()+"/requests";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(com.android.volley.Request.Method.GET,url,null,
                response -> {
                    requestList = RequestJsonConverter.convertListFromJson(response);
                    String[] name = new String[requestList.size()];
                    for(int i=0;i<requestList.size();i++) {
                        name[i]=employee.getFirstName()+" "+employee.getLastName();
                    }
                    Collections.sort(requestList);
                    RequestAdapter requestAdapter = new RequestAdapter(getContext(),requestList, Arrays.asList(name),this);
                    rvRequests.setAdapter(requestAdapter);
                    rvRequests.setLayoutManager(new LinearLayoutManager(getContext()));
                },
                error -> {
                    Toast.makeText(getContext(),"Something went wrong. Please try again later.",Toast.LENGTH_SHORT).show();
                });
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(arrayRequest);

    }

    @Override
    public void onRequestClick(int position) {
        Request request = requestList.get(position);
        String employeeName = employee.getFirstName()+ " " +employee.getLastName();

        if(request.getStatus().equals("PENDING")==false) {
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Action");
        String[] options = new String[]{"Accept","Deny","Details"};
        dialog.setItems(options,
                (d,w)->{
                    switch (w){
                        case 0: {
                            request.setStatus("ACCEPTED");
                            try {
                                modifyRequest(request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 1:{
                            request.setStatus("REFUSED");
                            try {
                                modifyRequest(request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 2:{
                            dRequestDetails = new Dialog(getContext());
                            dRequestDetails.setContentView(R.layout.dialog_show_details_about_request);
                            Window window = dRequestDetails.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            window.getAttributes().windowAnimations = R.style.CustomDialogAnimation;
                            TextView tvName = dRequestDetails.findViewById(R.id.tvRequestDetailsName);
                            TextView tvCreateDate = dRequestDetails.findViewById(R.id.tvRequestDetailsCreateDate);
                            TextView tvRequestDate = dRequestDetails.findViewById(R.id.tvRequestDetailsDate);
                            TextView tvNumberOfHours = dRequestDetails.findViewById(R.id.tvRequestDetailsNumberOfHours);
                            TextView tvReason = dRequestDetails.findViewById(R.id.tvRequestDetailsReason);
                            TextView tvType = dRequestDetails.findViewById(R.id.tvRequestDetailsType);

                            tvName.setText("Name: "+employeeName);
                            tvCreateDate.setText("Created on:"+request.getCreateDate().toString());
                            tvRequestDate.setText("Requested date: "+ request.getRequestDate().toString());
                            tvNumberOfHours.setText("Number of hours requested: " +request.getNumberOfHours()+" ");
                            tvReason.setText("Reason: "+request.getReason());
                            tvType.setText("Type of request: "+((request.getType()=="ADD_HOURS")? "Overtime work": "Early leave"));

                            dRequestDetails.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dRequestDetails.show();
                        }

                    }
                }).show();
    }

    private void modifyRequest(Request request) throws JSONException {
        String url = getString(R.string.url)+"requests/"+request.getId();
        JsonObjectRequest objectRequest = new JsonObjectRequest(com.android.volley.Request.Method.PATCH,url,
                RequestJsonConverter.convertToJson(request).put("employeeLogId",manager.getId()),
                response -> {
                    Toast.makeText(getContext(), "Request modified successfully!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(objectRequest);
        getRequests(employee);
    }
}