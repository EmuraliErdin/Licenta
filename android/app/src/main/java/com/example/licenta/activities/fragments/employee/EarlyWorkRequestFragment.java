package com.example.licenta.activities.fragments.employee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.Log;
import com.example.licenta.classes.Request;
import com.example.licenta.classes.converters.LogJsonConverter;
import com.example.licenta.classes.converters.RequestJsonConverter;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class EarlyWorkRequestFragment extends Fragment {

    private Employee employee;
    EditText edtReason;
    EditText edtNumberOfHours;
    CalendarView calendarView;
    Button btnSubmit;
    LocalDate date;


    public EarlyWorkRequestFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        employee = (Employee) bundle.getSerializable("employee");

        return inflater.inflate(R.layout.fragment_early_work_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setupListeners();
    }

    private void setupListeners() {
        btnSubmit.setOnClickListener(v->{
            if(validateInput()==false) {
                Toast.makeText(getContext(),"Field validation went wrong. Please fill all the fields. \n" +
                        "The numeric field should be a positive number",Toast.LENGTH_LONG).show();
                return;
            }
            Request request = formRequest();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                    getString(R.string.url)+"requests", RequestJsonConverter.convertToJson(request),
                    response -> {Toast.makeText(getContext(),"The request was saved successfully!",Toast.LENGTH_SHORT).show();},
                    error -> {Toast.makeText(getContext(),"The request could not be saved",Toast.LENGTH_SHORT).show();});

            Log log = new Log();
            log.setCreateDate(LocalDate.now());
            log.setAction(employee.getFirstName()+" "+employee.getLastName()+" requested a "+request.getType()+" type of request on: "+log.getCreateDate());
            log.setEmployeeID(employee.getId());

            JsonObjectRequest jsonActionRequest =  new JsonObjectRequest(com.android.volley.Request.Method.POST,
                    getString(R.string.url)+"logs", LogJsonConverter.convertToJson(log),
                    response -> {},
                    error -> {});

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);
            requestQueue.add(jsonActionRequest);

        });
        calendarView.setOnDateChangeListener((v,y,m,d)-> {

            date = LocalDate.of(y, m+1, d);
        });

    }

    private Request formRequest() {
        Request request = new Request();

        request.setNumberOfHours(Integer.parseInt(edtNumberOfHours.getText().toString()));
        request.setNumberOfHours(-request.getNumberOfHours());

        request.setRequestDate(date);
        request.setEmployeeId(employee.getId());

        LocalDateTime now = LocalDateTime.now();
        request.setCreateDate(now);

        request.setType("SUBTRACT_HOURS");

        request.setStatus("PENDING");

        request.setReason(edtReason.getText().toString());

        return request;
    }

    private boolean validateInput() {
        if(edtReason.getText().toString().length()<=3) {
            return false;
        }
        try{
            if(Integer.parseInt(edtNumberOfHours.getText().toString())<=0) {
                return false;
            }

        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    private void init() {

        date = LocalDate.now();
        edtNumberOfHours = getView().findViewById(R.id.edtEmployeeFragmentEarlyNumberOfHours);
        edtReason = getView().findViewById(R.id.edtEmployeeFragmentEarlyReason);
        calendarView =  getView().findViewById(R.id.calendarEmployeeFragmentEarly);
        btnSubmit =  getView().findViewById(R.id.btnEmployeeFragmentEarlySubmit);

    }


}