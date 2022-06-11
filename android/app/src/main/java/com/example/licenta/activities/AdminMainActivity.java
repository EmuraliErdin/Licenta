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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.licenta.R;
import com.example.licenta.activities.fragments.admin.ChangeEmployeeDepartmentFragment;
import com.example.licenta.activities.fragments.admin.CreateDepartmentFragment;
import com.example.licenta.activities.fragments.admin.CreatePrizeFragment;
import com.example.licenta.activities.fragments.admin.IssueListFragment;
import com.example.licenta.activities.fragments.admin.RenameDepartmentFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView tvName;
    TextView tvEmail;
    TextView tvLevel;
    TextView tvProgress;
    ProgressBar pbLevel;
    CreateDepartmentFragment createDepartmentFragment = new CreateDepartmentFragment();
    CreatePrizeFragment createPrizeFragment = new CreatePrizeFragment();
    RenameDepartmentFragment renameDepartmentFragment = new RenameDepartmentFragment();
    ChangeEmployeeDepartmentFragment changeEmployeeDepartmentFragment = new ChangeEmployeeDepartmentFragment();
    IssueListFragment issueListFragment = new IssueListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        init();
        setUpListener();
    }

    private void setUpListener() {
        //set up navigation drawer
        navigationView.setNavigationItemSelectedListener(v->{
            switch (v.getItemId())
            {
                case R.id.item_make_department:
                    openFragment(createDepartmentFragment);
                    break;
                case R.id.item_create_prize:
                    openFragment(createPrizeFragment);
                    break;
                case R.id.item_rename_department:
                    openFragment(renameDepartmentFragment);
                    break;
                case R.id.item_change_department:
                    openFragment(changeEmployeeDepartmentFragment);
                    break;
                case R.id.item_check_issues:
                    openFragment(issueListFragment);
                    break;
            }
            return true;
        });
    }

    //Initialize the views
    private void init() {
        navigationView = findViewById(R.id.navigation_view_admin);
        toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutAdmin);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_toggle, R.string.close_toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
        openFragment(createPrizeFragment);

        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmail);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvProgress = navigationView.getHeaderView(0).findViewById(R.id.tvNavProgress);
        tvLevel = navigationView.getHeaderView(0).findViewById(R.id.tvNavLevel);
        pbLevel = navigationView.getHeaderView(0).findViewById(R.id.pbNavLevel);
        pbLevel.setProgress(0);
        tvEmail.setText("");
        tvName.setText("Hello Admin!");
        tvProgress.setText("");
        tvLevel.setText("");
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_admin,fragment);
        fragmentTransaction.commit();
    }



}