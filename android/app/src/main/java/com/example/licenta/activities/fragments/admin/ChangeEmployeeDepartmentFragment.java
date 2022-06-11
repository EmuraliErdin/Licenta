package com.example.licenta.activities.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Department;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.converters.DepartmentJsonConverter;
import com.example.licenta.classes.converters.EmployeeJsonConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ChangeEmployeeDepartmentFragment extends Fragment {
    Spinner spinOldDepartment;
    Spinner spinNewDepartment;
    Spinner spinEmployees;
    Button btnSubmit;
    private List<Department> departmentList;
    private String[] departmentNames;
    private List<Employee> employeeList;
    private String[] employeeNames;


    public ChangeEmployeeDepartmentFragment() {}

    public static ChangeEmployeeDepartmentFragment newInstance(String param1, String param2) {
        ChangeEmployeeDepartmentFragment fragment = new ChangeEmployeeDepartmentFragment();
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

        return inflater.inflate(R.layout.fragment_change_employee_department, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInit();
        getDepartments();
        //getEmployeesOfDepartment();
        setUpListeners();

    }

    private void setUpListeners() {
        btnSubmit.setOnClickListener(v->{
            if(spinOldDepartment.getSelectedItemPosition()!=spinNewDepartment.getSelectedItemPosition()){
                Employee employee = employeeList.get(spinEmployees.getSelectedItemPosition());
                employee.setDepartmentId(departmentList.get(spinNewDepartment.getSelectedItemPosition()).getId());
                String url = getString(R.string.url)+"employees/"+employee.getId();
                JsonObjectRequest jsonObjectRequest = null;
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("departmentId", employee.getDepartmentId());
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url,
                            jsonObject,
                            response -> {
                                Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT);
                            },
                            error -> {
                                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT);
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
            }
        });
        spinOldDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getEmployeesOfDepartment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getEmployeesOfDepartment() {

        final Department selectedDepartment = departmentList.get(spinOldDepartment.getSelectedItemPosition());
        String url = getString(R.string.url) + "departments/" + selectedDepartment.getId().toString() + "/employees";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    employeeList = EmployeeJsonConverter.convertListFromJson(response);
                    employeeNames = new String[employeeList.size()];
                    for (int j=0; j < employeeList.size(); j++) {
                        employeeNames[j] = employeeList.get(j).getEmail();
                    }
                    //department spinner set
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, employeeNames);
                    spinEmployees.setAdapter(spinnerAdapter);
                },
                error -> Toast.makeText(getContext(),"Something went wrong.",Toast.LENGTH_SHORT).show());

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(jsonArrayRequest);

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
                    ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, departmentNames);
                    spinNewDepartment.setAdapter(spinnerAdapter2);
                },
                error -> Toast.makeText(getContext(),"Something went wrong.",Toast.LENGTH_SHORT).show());

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(jsonArrayRequest);
    }

    private void onInit() {
        spinOldDepartment = getView().findViewById(R.id.spinOldDepartment);
        spinNewDepartment = getView().findViewById(R.id.spinNewDepartment);
        spinEmployees = getView().findViewById(R.id.spinEmployee);
        btnSubmit = getView().findViewById(R.id.btnSubmitChangeEmployeeDepartment);
    }


}