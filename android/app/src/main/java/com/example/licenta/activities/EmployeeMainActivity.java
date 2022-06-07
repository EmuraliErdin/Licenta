package com.example.licenta.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.activities.fragments.employee.EarlyWorkRequestFragment;
import com.example.licenta.activities.fragments.employee.MyRequestsFragment;
import com.example.licenta.activities.fragments.employee.OvertimeWorkRequestFragment;
import com.example.licenta.activities.fragments.ProfileFragment;
import com.example.licenta.activities.fragments.ReportAProblemFragment;
import com.example.licenta.activities.fragments.manager.EmployeeRequestsFragment;
import com.example.licenta.classes.Employee;
import com.example.licenta.classes.Experience;
import com.example.licenta.classes.Role;
import com.example.licenta.classes.converters.ExperienceJsonConverter;
import com.example.licenta.classes.converters.RoleJsonConverter;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.List;

public class EmployeeMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView tvName;
    TextView tvEmail;
    TextView tvLevel;
    TextView tvProgress;
    ProgressBar pbLevel;
    Employee employee;
    ProfileFragment profileFragment = new ProfileFragment();
    OvertimeWorkRequestFragment overtimeWorkRequestFragment = new OvertimeWorkRequestFragment();
    EarlyWorkRequestFragment earlyWorkRequestFragment = new EarlyWorkRequestFragment();
    MyRequestsFragment myRequestsFragment = new MyRequestsFragment();
    ReportAProblemFragment reportAProblemFragment = new ReportAProblemFragment();
    EmployeeRequestsFragment employeeRequestsFragment = new EmployeeRequestsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        init();
        setUpListener();
        setUpFragments();

    }
    //sets up the only listener available
    private void setUpListener() {
        //set up navigation drawer
        navigationView.setNavigationItemSelectedListener(v->{
            switch (v.getItemId())
            {
                case R.id.item_add_time:
                    openFragment(overtimeWorkRequestFragment);
                    break;
                case R.id.item_subtract_time:
                    openFragment(earlyWorkRequestFragment);
                    break;
                case R.id.item_my_requests:
                    openFragment(myRequestsFragment);
                    break;
                case R.id.item_profile: {
                    openFragment(profileFragment);
                    break;
                }
                case R.id.report_a_problem:{
                    openFragment(reportAProblemFragment);
                    break;
                }
                case R.id.item_colleagues_requests:{
                    openFragment(employeeRequestsFragment);
                    break;
                }
            }
            return true;
        });
    }

    //sets up the fragments with the bundles that have the employees in them
    private void setUpFragments() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("employee",employee);
        profileFragment.setArguments(bundle);
        overtimeWorkRequestFragment.setArguments(bundle);
        earlyWorkRequestFragment.setArguments(bundle);
        myRequestsFragment.setArguments(bundle);
        employeeRequestsFragment.setArguments(bundle);
    }

    //Initialize the views
    private void init() {
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_toggle, R.string.close_toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        employee = (Employee) getIntent().getSerializableExtra("employee");
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmail);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvProgress = navigationView.getHeaderView(0).findViewById(R.id.tvNavProgress);
        tvLevel = navigationView.getHeaderView(0).findViewById(R.id.tvNavLevel);
        pbLevel = navigationView.getHeaderView(0).findViewById(R.id.pbNavLevel);
        pbLevel.setProgress(0);

        tvEmail.setText(employee.getEmail());
        tvName.setText("Hello! "+employee.getLastName() + " " + employee.getFirstName());
        MenuItem v = navigationView.getMenu().findItem(R.id.item_colleagues_requests);

        v.setVisible(false);
        checkForRoles();
        getXP();
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    private void checkForRoles() {
        String url = getString(R.string.url)+"employees/"+employee.getId()+"/roles";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                response -> {
                    Role role = RoleJsonConverter.convertFromJson(response);
                    if(role!=null && LocalDate.now().isBefore(role.getEndDate())==true){
                        MenuItem v = navigationView.getMenu().findItem(R.id.item_colleagues_requests);
                        v.setVisible(true);
                    }
                },
                error -> {});

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

    private void getXP() {
        String url = getString(R.string.url)+"employees/"+employee.getId()+"/experiences";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Experience> experienceList = ExperienceJsonConverter.convertListFromJson(response);
                    Integer level = Experience.getLevel(experienceList);
                    pbLevel.setMax(level*100);
                    Integer progress = Experience.getProgress(experienceList);
                    pbLevel.setProgress(progress);
                    tvProgress.setText("Experience: "+progress+"/"+level*100);
                    tvLevel.setText("Current level: "+level.toString());
                },
                error -> {
                    tvLevel.setText("Current level: "+ 1);
                    pbLevel.setProgress(0);
                });
        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);

    }

}