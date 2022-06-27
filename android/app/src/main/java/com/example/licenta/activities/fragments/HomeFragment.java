package com.example.licenta.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.licenta.R;
import com.example.licenta.classes.Employee;


public class HomeFragment extends Fragment {
    TextView tvLevel;
    TextView tvExperience;
    Employee employee;

    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        employee = (Employee) bundle.getSerializable("employee");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        tvLevel = getView().findViewById(R.id.tvLevel);
        tvExperience = getView().findViewById(R.id.tvExperience);
        tvLevel.setText("Level: "+employee.getLevel());
        tvExperience.setText(""+employee.getExperience()+"/"+100*employee.getLevel());
    }
}