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
import com.example.licenta.classes.Experience;
import com.example.licenta.classes.Log;
import com.example.licenta.classes.Request;
import com.example.licenta.classes.adapters.RequestAdapter;
import com.example.licenta.classes.converters.ExperienceJsonConverter;
import com.example.licenta.classes.converters.LogJsonConverter;
import com.example.licenta.classes.converters.RequestJsonConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeeRequestsFragment extends Fragment implements RequestAdapter.OnRequestListener {

    private RecyclerView rvRequests;
    private Employee employee;
    private List<Request> requestList;
    private List<String> nameList;
    private Dialog dRequestDetails;

    public EmployeeRequestsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        employee = (Employee) getArguments().getSerializable("employee");

        return inflater.inflate(R.layout.fragment_employee_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        rvRequests = getView().findViewById(R.id.rvManagerFragmentRequests);
        getRequestsFromDepartment();
    }

    //Get and attach list of requests to recyclerview
    private void getRequestsFromDepartment() {
        String url = getString(R.string.url)+"/departments/"+employee.getDepartmentId()+"/requests";

        JsonArrayRequest request = new JsonArrayRequest(com.android.volley.Request.Method.GET,url,null,
                response -> {
                        getRequestList(response);
                },
                error -> {
                    Toast.makeText(getContext(),"Something went wrong.",Toast.LENGTH_SHORT).show();
        });
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);

    }

    private void getRequestList(JSONArray response) {
        try {
            requestList = RequestJsonConverter.convertListFromJson(response);
            nameList = new ArrayList<>(requestList.size());

            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);
                nameList.add(jsonObject.getString("name"));
            }
            RequestAdapter requestAdapter = new RequestAdapter(getContext(), requestList, nameList,this);
            rvRequests.setAdapter(requestAdapter);
            rvRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestClick(int position) {
        Request request = requestList.get(position);
        String name = nameList.get(position);

        if(!request.getStatus().equals("PENDING")) {
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
                                modifyRequest(request,nameList.get(position));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 1:{
                            request.setStatus("REFUSED");
                            try {
                                modifyRequest(request, nameList.get(position));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 2:{
                            dRequestDetails = new Dialog(getContext());
                            dRequestDetails.setContentView(R.layout.dialog_show_details_about_request);
                            Window window = dRequestDetails.getWindow();
                            window.getAttributes().windowAnimations = R.style.CustomDialogAnimation;
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView tvName = dRequestDetails.findViewById(R.id.tvRequestDetailsName);
                            TextView tvCreateDate = dRequestDetails.findViewById(R.id.tvRequestDetailsCreateDate);
                            TextView tvRequestDate = dRequestDetails.findViewById(R.id.tvRequestDetailsDate);
                            TextView tvNumberOfHours = dRequestDetails.findViewById(R.id.tvRequestDetailsNumberOfHours);
                            TextView tvReason = dRequestDetails.findViewById(R.id.tvRequestDetailsReason);
                            TextView tvType = dRequestDetails.findViewById(R.id.tvRequestDetailsType);

                            tvName.setText("Name: "+name);
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

    private void modifyRequest(Request request, String name) throws JSONException {
        String url = getString(R.string.url) + "requests/" + request.getId();
        JsonObjectRequest objectRequest = new JsonObjectRequest(com.android.volley.Request.Method.PATCH, url,
                RequestJsonConverter.convertToJson(request).put("employeeLogId", employee.getId()),
                response -> {
                    Toast.makeText(getContext(), "Request modified successfully!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                });
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(objectRequest);


//        Log log = new Log();
//        log.setEmployeeID(employee.getId());
//        log.setAction(Log.modifyEmployeeRequest(employee.getFirstName()+" "+employee.getLastName()
//                , name, request.getStatus(),request.getRequestDate()));
//        log.setCreateDate(LocalDate.now());
//
//        JsonObjectRequest logRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,logUrl,
//                LogJsonConverter.convertToJson(log),
//                response -> {},
//                error -> {});
//        rq.add(logRequest);
//        getRequestsFromDepartment();

    }
}