package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;


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

import java.util.List;


public class CreateAccountActivity extends AppCompatActivity {
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtPassword;
    EditText edtEmail;
    CheckBox cbIsManager;
    Spinner spinDepartment;
    Button btnCreateAccount;
    Button btnBack;
    List<Department> departmentList;
    String[] departmentNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        init();
        setupButtons();
    }

    //sets up all the listeners needed
    private void setupButtons() {
        btnBack.setOnClickListener(v-> finish());
        btnCreateAccount.setOnClickListener(v -> {
            Employee employee = createEmployee();

            if (employee != null) {
                Intent intent = new Intent(this, EmailCodeActivity.class);
                intent.putExtra("employee", employee);


                String url = getResources().getString(R.string.url) + "firstPartCreateAccount";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, EmployeeJsonConverter.convertToJson(employee),
                        response -> {startActivity(intent); },
                        error -> Toast.makeText(this, "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show()
                        );
                RequestQueue rq = Volley.newRequestQueue(this);
                rq.add(jsonObjectRequest);
            }
        });
    }

    //creates an employee from the inputs
    private Employee createEmployee() {
        if (!validate()) {
            return null;
        }
        Employee employee = new Employee();
        employee.setFirstName(edtFirstName.getText().toString());
        employee.setLastName(edtLastName.getText().toString());
        employee.setEmail(edtEmail.getText().toString());
        employee.setPassword(edtPassword.getText().toString());
        employee.setDepartmentId(departmentList.get(spinDepartment.getSelectedItemPosition()).getId());
        employee.setIsManager(cbIsManager.isChecked());
        return employee;
    }

    //validate input
    private boolean validate() {

        if (edtFirstName.getText().toString().length() < 3) {
            return false;
        }

        if (edtLastName.getText().toString().length() < 3) {
            return false;
        }

        if (edtEmail.getText().toString().length() < 3 || !edtEmail.getText().toString().contains("@")) {
            return false;
        }

        if (edtPassword.getText().toString().length() < 6) {
            return false;
        }

        return spinDepartment.getSelectedItemId() != -1;
    }

    //Initialize the views
    private void init() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtPassword = findViewById(R.id.edtPasswordCreateAccount);
        edtEmail = findViewById(R.id.edtEmail);
        cbIsManager = findViewById(R.id.cbIsManager);
        spinDepartment = findViewById(R.id.spinDepartment);
        btnBack = findViewById(R.id.btnBack);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        getDepartments();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    //get a list of all departments and putting it in the spinner
    private void getDepartments() {

        String url = getString(R.string.url)+"departments";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    departmentList = DepartmentJsonConverter.convertListFromJson(response);
                    departmentNames = new String[departmentList.size()];
                    for (int i=0;i<departmentList.size();i++) {
                        departmentNames[i] = departmentList.get(i).getTitle();
                    }

                    //department spinner set
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, departmentNames);
                    spinDepartment.setAdapter(spinnerAdapter);

                },
                error -> Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_SHORT).show());

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

    }

}