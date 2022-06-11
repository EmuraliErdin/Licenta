package com.example.licenta.activities.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Department;
import com.example.licenta.classes.converters.DepartmentJsonConverter;

import java.util.List;


public class RenameDepartmentFragment extends Fragment {

    private Button btnSubmit;
    private EditText edtNewDepartmentName;
    private Spinner spinOldDepartment;
    private List<Department> departmentList;
    private String[] departmentNames;

    public RenameDepartmentFragment() {

    }

    public static RenameDepartmentFragment newInstance(String param1, String param2) {
        RenameDepartmentFragment fragment = new RenameDepartmentFragment();
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
        return inflater.inflate(R.layout.fragment_rename_department, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        getDepartments();
        setUpListener();
    }

    private void setUpListener() {
        btnSubmit.setOnClickListener(v->{
            if(edtNewDepartmentName.getText().toString().trim().length()>=3){
                String departmentId = departmentList.get(spinOldDepartment.getSelectedItemPosition()).getId();
                String departmentName = edtNewDepartmentName.getText().toString();
                departmentNames[spinOldDepartment.getSelectedItemPosition()] = departmentName;
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, departmentNames);
                spinOldDepartment.setAdapter(spinnerAdapter);
                String url = getString(R.string.url)+"departments/"+departmentId;
                Department d = new Department(edtNewDepartmentName.getText().toString());
                JsonObjectRequest jsonObjectRequest=  new JsonObjectRequest(Request.Method.PUT,url,DepartmentJsonConverter.convertToJson(d),
                        response -> {
                            Toast.makeText(getContext(),"Success!",Toast.LENGTH_SHORT);
                        },
                        error -> {
                            Toast.makeText(getContext(),"An error occurred",Toast.LENGTH_SHORT);
                        });
                Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
            }else{
                Toast.makeText(getContext(),"Department name should be at least 3 characters.",Toast.LENGTH_SHORT);
            }
        });
    }

    public void getDepartments(){
        String url = getString(R.string.url)+"departments";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    departmentList = DepartmentJsonConverter.convertListFromJson(response);
                    departmentNames = new String[departmentList.size()];
                    for (int i=0;i<departmentList.size();i++) {
                        departmentNames[i] = departmentList.get(i).getTitle();
                    }
                    //department spinner set
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, departmentNames);
                    spinOldDepartment.setAdapter(spinnerAdapter);
                },
                error -> Toast.makeText(getContext(),"Something went wrong.",Toast.LENGTH_SHORT).show());

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(jsonArrayRequest);
    }

    private void init() {
        spinOldDepartment = getView().findViewById(R.id.spinExistingDepartments);
        edtNewDepartmentName = getView().findViewById(R.id.edtNewDepartmentName);
        btnSubmit = getView().findViewById(R.id.btnSubmitDepartmentNamee);
    }
}