package com.example.licenta.activities.fragments.manager;

import android.content.pm.ActivityInfo;
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

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.Log;
import com.example.licenta.classes.adapters.LogAdapter;
import com.example.licenta.classes.converters.LogJsonConverter;

import java.util.List;


public class EmployeeLogFragment extends Fragment {

    RecyclerView rvLogs;
    Employee employee;
    List<Log> logList;

    public EmployeeLogFragment() {

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

        return inflater.inflate(R.layout.fragment_employee_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        rvLogs = getView().findViewById(R.id.rvLogs);
        getLogs();
    }
    private void getLogs(){
        String url = getString(R.string.url)+"employees/"+employee.getId()+"/logs";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    logList = LogJsonConverter.convertListFromJson(response);
                    LogAdapter logAdapter = new LogAdapter(logList,getContext());
                    rvLogs.setAdapter(logAdapter);
                    rvLogs.setLayoutManager(new LinearLayoutManager(getContext()));
                },
                error -> {
                    Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                });
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }
}