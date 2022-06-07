package com.example.licenta.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Issue;
import com.example.licenta.classes.converters.IssueJsonConverter;

import java.time.LocalDate;


public class ReportAProblemFragment extends Fragment {

    EditText edtReason;
    Button btnSubmit;
    Spinner spinPriorityLevel;
    Issue issue;

    public ReportAProblemFragment() {

    }


    public static ReportAProblemFragment newInstance() {
        ReportAProblemFragment fragment = new ReportAProblemFragment();
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

        return inflater.inflate(R.layout.fragment_report_a_problem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setupListeners();

    }

    private void init() {
        edtReason = getView().findViewById(R.id.edtIssueFragmentReason);
        btnSubmit = getView().findViewById(R.id.btnIssueFragmentSubmit);
        spinPriorityLevel = getView().findViewById(R.id.spinProblemFragmentLevel);
    }

    private void setupListeners() {
        btnSubmit.setOnClickListener(v->{
            if(validateInput()==false) {
                Toast.makeText(getContext(),"Input should be between 5 and 30 characters.",Toast.LENGTH_SHORT).show();
                return;
            }
            createIssue();

            String url = getString(R.string.url)+"issues";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url, IssueJsonConverter.convertToJson(issue),
                    response -> Toast.makeText(getContext(),"The report has been created successfully!",Toast.LENGTH_SHORT).show(),
                    error -> Toast.makeText(getContext(),"The report could not be saved.", Toast.LENGTH_SHORT).show());

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);

        });
    }

    private void createIssue() {
        issue = new Issue();
        issue.setReason(edtReason.getText().toString());
        issue.setCreateDate(LocalDate.now());
        issue.setStatus("PENDING");
        issue.setPriorityLevel(spinPriorityLevel.getSelectedItem().toString());
    }

    private boolean validateInput() {
        if(edtReason.getText().toString().length()<=5 || edtReason.getText().toString().length()>=30) {
            return false;
        }
        return true;
    }

}