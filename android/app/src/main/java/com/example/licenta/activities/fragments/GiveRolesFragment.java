package com.example.licenta.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.Log;
import com.example.licenta.classes.Role;
import com.example.licenta.classes.converters.LogJsonConverter;
import com.example.licenta.classes.converters.RoleJsonConverter;

import java.time.LocalDate;
import java.util.Calendar;

public class GiveRolesFragment extends Fragment {

    private Employee manager;
    private Employee employee;
    private TextView tvName;
    private Spinner spinRoles;
    private CalendarView calendar;
    private Button btnSubmit;
    private TextView tvCurrentRole;
    LocalDate date;

    public GiveRolesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        manager = (Employee) bundle.getSerializable("manager");
        employee = (Employee) bundle.getSerializable("employee");
        return inflater.inflate(R.layout.fragment_give_roles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setupListeners();
    }

    private void setupListeners(){

        calendar.setOnDateChangeListener((v,y,m,d)-> {
            date = LocalDate.of(y, m+1, d);
        });

        btnSubmit.setOnClickListener(v->{
            Role role = formRole();
            String url = getString(R.string.url)+"accesses";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,
                    RoleJsonConverter.convertToJson(role),
                    response -> {
                        Toast.makeText(getContext(), "Successfully changed role!", Toast.LENGTH_SHORT).show();
                    },
                    error -> {Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();});

            Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
            String logUrl = getString(R.string.url)+"logs";

            Log log = new Log();
            log.setEmployeeID(manager.getId());
            log.setAction(Log.modifyEmployeeRole(manager.getFirstName()+" "+manager.getLastName()
                    , employee.getFirstName() + employee.getLastName(), role.getType(),role.getEndDate()));
            log.setCreateDate(LocalDate.now());

            JsonObjectRequest logRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,logUrl,
                    LogJsonConverter.convertToJson(log),
                    response -> {},
                    error -> {});
            Volley.newRequestQueue(getContext()).add(logRequest);

        });
    }

    private Role formRole() {
        Role role = new Role();
        role.setStartDate(LocalDate.now());
        role.setEndDate(date);
        role.setGivenTo(employee.getId());
        role.setFrom(manager.getId());
        role.setType(spinRoles.getSelectedItem().toString());
         return role;
    }

    private void init() {
        date = LocalDate.now();
        btnSubmit = getView().findViewById(R.id.btnChangeRole);
        tvName = getView().findViewById(R.id.tvGiveRole);
        spinRoles = getView().findViewById(R.id.spinRoles);
        calendar = getView().findViewById(R.id.calendarRoleStartDate);

        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        calendar.setMinDate(now);

        tvCurrentRole = getView().findViewById(R.id.tvCurrentRole);
        tvName.setText(tvName.getText().toString()+ " "+employee.getFirstName()+" "+employee.getLastName());
        getCurrentRole();

    }

    private void getCurrentRole(){
        String url = getString(R.string.url)+"employees/"+employee.getId()+"/roles";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                response -> {
                    Role role = RoleJsonConverter.convertFromJson(response);
                    switch (role.getType()) {
                        case "READ_WRITE_COLLEAGUES":
                            spinRoles.setSelection(1);
                            break;
                        case "NONE":
                            spinRoles.setSelection(0);
                            break;
                    }
                    tvCurrentRole.setText("Current role is "+role.getType()+" and it is effective until "+role.getEndDate().toString());
                },
                error -> {spinRoles.setSelection(0);});
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(jsonObjectRequest);
    }
}