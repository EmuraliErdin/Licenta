package com.example.licenta.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.converters.EmployeeJsonConverter;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Employee employee;
    TextView tvName;
    TextView tvChangePassword;
    EditText edtEmail;
    EditText edtOldPassword;
    EditText edtNewPassword;
    ImageButton imgBtnEditEmail;
    ImageButton imgBtnAcceptChanges;
    ImageButton imgBtnCancel;
    Button btnChangePassword;
    boolean passwordChange=false;


    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setupListeners();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        employee = (Employee) bundle.getSerializable("employee");

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void setupListeners() {

        imgBtnEditEmail.setOnClickListener(v -> {
            imgBtnEditEmail.setVisibility(View.INVISIBLE);
            imgBtnAcceptChanges.setVisibility(View.VISIBLE);
            imgBtnCancel.setVisibility(View.VISIBLE);
            edtEmail.setEnabled(true);
        });

        imgBtnCancel.setOnClickListener(v -> {
            edtEmail.setEnabled(false);
            edtEmail.setText(employee.getEmail());
            imgBtnCancel.setVisibility(View.INVISIBLE);
            imgBtnAcceptChanges.setVisibility(View.INVISIBLE);
            imgBtnEditEmail.setVisibility(View.VISIBLE);
        });

        imgBtnAcceptChanges.setOnClickListener(v -> {
            edtEmail.setEnabled(false);
            imgBtnCancel.setVisibility(View.INVISIBLE);
            imgBtnAcceptChanges.setVisibility(View.INVISIBLE);
            imgBtnEditEmail.setVisibility(View.VISIBLE);

            employee.setEmail(edtEmail.getText().toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                    getResources().getString(R.string.url) + "employees/" + employee.getId(),
                    EmployeeJsonConverter.convertToJson(employee),
                    response -> Log.d("PUT", "SUCCSESSFULL"),
                    error -> Log.d("PUT", "FAILED")
            );
            RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
            requestQueue.add(jsonObjectRequest);

        });

        tvChangePassword.setOnClickListener(v -> {
            if (passwordChange == false) {
                passwordChange = true;
                edtNewPassword.setVisibility(View.VISIBLE);
                edtOldPassword.setVisibility(View.VISIBLE);
                btnChangePassword.setVisibility(View.VISIBLE);
            } else {
                passwordChange = false;
                edtNewPassword.setVisibility(View.INVISIBLE);
                edtOldPassword.setVisibility(View.INVISIBLE);
                btnChangePassword.setVisibility(View.INVISIBLE);
            }
        });

        btnChangePassword.setOnClickListener(v -> {
            if (edtNewPassword.getText().toString().length() >= 8 && edtNewPassword.getText().toString().length() <= 16) {

                try {
                    JSONObject jsonObject = EmployeeJsonConverter.convertToJson(employee);
                    jsonObject.put("newPassword", edtNewPassword.getText().toString())
                            .put("oldPassword",edtOldPassword.getText().toString());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH,
                            getResources().getString(R.string.url) + "employeeChangePassword/" + employee.getId(),
                            jsonObject,
                            response -> {
                                Toast.makeText(getView().getContext(), "Password changed successfully!.", Toast.LENGTH_LONG).show();
                            },
                            error -> {
                                Toast.makeText(getView().getContext(), "The passwords don't match.", Toast.LENGTH_LONG).show();
                            });

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(jsonObjectRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getView().getContext(), "The password must have between 8 and 16 characters.", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void init()
    {
        tvName = getView().findViewById(R.id.tvEmployeeFragmentName);
        tvName.setText(employee.getFirstName()+" "+employee.getLastName());
        edtEmail = getView().findViewById(R.id.edtEmployeeFragmentEmail);
        edtEmail.setText(employee.getEmail());
        tvChangePassword = getView().findViewById(R.id.tvEmployeeFragmentChangePassword);
        imgBtnEditEmail = getView().findViewById(R.id.btnEditEmail);
        imgBtnAcceptChanges = getView().findViewById(R.id.btnEmployeeFragmentAcceptChanges);
        imgBtnCancel = getView().findViewById(R.id.btnEmployeeFragmentCancelEditing);
        edtNewPassword = getView().findViewById(R.id.edtFragmentNewPassword);
        edtOldPassword = getView().findViewById(R.id.edtFragmentOldPassword);
        btnChangePassword =  getView().findViewById(R.id.btnEmployeFragmentSubmitPassword);
        edtEmail.setEnabled(false);
    }

}