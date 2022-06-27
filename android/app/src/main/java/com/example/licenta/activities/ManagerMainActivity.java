package com.example.licenta.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.activities.fragments.HomeFragment;
import com.example.licenta.activities.fragments.ProfileFragment;
import com.example.licenta.activities.fragments.ReportAProblemFragment;
import com.example.licenta.activities.fragments.manager.EmployeeListFragment;
import com.example.licenta.activities.fragments.manager.EmployeeRequestsFragment;
import com.example.licenta.classes.Employee;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ManagerMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView tvName;
    TextView tvEmail;
    TextView tvLevel;
    ProgressBar pbLevel;
    Employee employee;
    EmployeeRequestsFragment employeeRequestsFragment =new EmployeeRequestsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ReportAProblemFragment reportAProblemFragment = new ReportAProblemFragment();
    EmployeeListFragment employeeListFragment = new EmployeeListFragment();
    HomeFragment homeFragment = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
        init();
        openFragment(homeFragment);
        setUpFragments();
        setUpListener();
    }

    private void setUpListener() {
        //set up navigation drawer
        navigationView.setNavigationItemSelectedListener(v->{
            switch (v.getItemId())
            {
                case R.id.item_profile:
                    openFragment(profileFragment);
                    break;
                case R.id.item_home:
                    openFragment(homeFragment);
                    break;
                case R.id.item_employees_requests:
                    openFragment(employeeRequestsFragment);
                    break;
                case R.id.item_department_members: {
                    openFragment(employeeListFragment);
                    break;
                }
                case R.id.report_a_problem:{
                    openFragment(reportAProblemFragment);
                }
                break;
            }
            return true;
        });
    }

    private void init() {
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        navigationView = findViewById(R.id.navigation_view_manager);
        toolbar = findViewById(R.id.toolbar_manager);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutManager);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_toggle, R.string.close_toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        employee = (Employee) getIntent().getSerializableExtra("employee");

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.darkgreen));
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvLevel = navigationView.getHeaderView(0).findViewById(R.id.tvNavLevel);
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmail);
        pbLevel = navigationView.getHeaderView(0).findViewById(R.id.pbNavLevel);
        tvEmail.setText(employee.getEmail());

        tvName.setText("Hello "+employee.getFirstName()+" "+employee.getLastName()+"!");

        Integer level = employee.getLevel();
        pbLevel.setMax(level*100);
        Integer progress = employee.getExperience();
        pbLevel.setProgress(progress);
        tvLevel.setText(level.toString());

    }

    //sets up the fragments with the bundles that have the employees in them
    private void setUpFragments() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("employee",employee);
        employeeRequestsFragment.setArguments(bundle);
        profileFragment.setArguments(bundle);
        employeeListFragment.setArguments(bundle);
        homeFragment.setArguments(bundle);
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.fragment_container_manager,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}