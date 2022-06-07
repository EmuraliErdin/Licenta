package com.example.licenta.activities.fragments.manager;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.activities.fragments.GiveRolesFragment;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.adapters.EmployeeAdapter;
import com.example.licenta.classes.converters.EmployeeJsonConverter;

import java.util.List;


public class EmployeeListFragment extends Fragment implements EmployeeAdapter.OnNoteListener {
    private RecyclerView rvEmployees;
    private Employee manager;
    private List<Employee> employeeList;
    private SingleEmployeeRequestsFragment singleEmployeeRequestsFragment = new SingleEmployeeRequestsFragment();
    private GiveRolesFragment giveRolesFragment = new GiveRolesFragment();
    private EmployeeLogFragment employeeLogFragment = new EmployeeLogFragment();

    public EmployeeListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        manager = (Employee) bundle.getSerializable("employee");
        return inflater.inflate(R.layout.fragment_employee_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        rvEmployees = getView().findViewById(R.id.rvEmployeeListFragment);
        getEmployeesOfDepartment();
    }

    private void getEmployeesOfDepartment() {
        String url = getString(R.string.url)+"/departments/"+manager.getDepartmentId()+"/employees";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    employeeList = EmployeeJsonConverter.convertListFromJson(response);
                    EmployeeAdapter employeeAdapter = new EmployeeAdapter(getContext(), employeeList,this);
                    rvEmployees.setAdapter(employeeAdapter);
                    rvEmployees.setLayoutManager(new LinearLayoutManager(getContext()));
                },
                error -> Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show());
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }

    @Override
    public void onEmployeeClick(int position) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Action");
        String[] options;

        if(employeeList.get(position).isManager()==true){
             options = new String[]{"See log"};
        }else {
             options = new String[]{"See log", "Give roles", "See requests"};
        }
        dialog.setItems(options,
                (d,w)->{
                    switch (w){
                        case 0: {
                            openFragment(employeeLogFragment,position);
                            break;
                        }
                        case 1:{
                            openFragment(giveRolesFragment,position);
                            break;
                        }
                        case 2:{
                            openFragment(singleEmployeeRequestsFragment, position);
                        }

                    }
                }).show();

    }

    private void openFragment(Fragment fragment, int position) {
        Employee employee = employeeList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("employee",employee);
        bundle.putSerializable("manager",manager);

        fragment.setArguments(bundle);
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_manager,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}