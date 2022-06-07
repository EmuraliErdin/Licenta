package com.example.licenta.activities.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Department;
import com.example.licenta.classes.converters.DepartmentJsonConverter;


public class CreateDepartmentFragment extends Fragment {

    EditText edtDepartmentName;
    Button btnSubmit;
    public CreateDepartmentFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_create_department, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setupListener();
    }

    private void init(){
        edtDepartmentName = getView().findViewById(R.id.edtDepartmentName);
        btnSubmit = getView().findViewById(R.id.btnSubmitDepartmentName);
    }

    private void setupListener(){
        btnSubmit.setOnClickListener(v->{
            if(edtDepartmentName.getText().toString().trim().length()>=3){
                Department department = new Department();
                department.setTitle(edtDepartmentName.getText().toString());
                String url = getString(R.string.url)+"departments";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,
                        DepartmentJsonConverter.convertToJson(department),
                        response -> {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        },
                        error -> {Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();});
                Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
            }else{
                Toast.makeText(getContext(),"Department name must have at least 3 characters",Toast.LENGTH_SHORT).show();
            }

        });
    }
}