package com.example.licenta.activities.fragments.employee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.Request;
import com.example.licenta.classes.adapters.RequestAdapter;
import com.example.licenta.classes.converters.RequestJsonConverter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MyRequestsFragment extends Fragment implements RequestAdapter.OnRequestListener {

    RecyclerView rvRequests;
    Employee employee;
    List<Request> requestList;
    List<String> nameList;

    public MyRequestsFragment() {

    }

    public static MyRequestsFragment newInstance(String param1, String param2) {
        MyRequestsFragment fragment = new MyRequestsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        employee = (Employee) bundle.getSerializable("employee");

        return inflater.inflate(R.layout.fragment_my_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void getRequests() {
       String url = getString(R.string.url) + "employees/"+employee.getId()+"/requests";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(com.android.volley.Request.Method.GET,url,null,
                response -> {
                    getRequestList(response);
                },
                error -> {
                    Toast.makeText(getContext(), "There are no requests for now.", Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);


    }

    private void getRequestList(JSONArray response) {
        try {
            requestList = RequestJsonConverter.convertListFromJson(response);
            nameList = new ArrayList<>();
            for (int i = 0; i <= requestList.size(); i++) {
                nameList.add(employee.getLastName()+" "+employee.getFirstName());
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


    private void init() {
        rvRequests = getView().findViewById(R.id.rvEmployeeFragmentRequests);
        getRequests();
    }

    @Override
    public void onRequestClick(int position) {
        Request request = requestList.get(position);
    }

}