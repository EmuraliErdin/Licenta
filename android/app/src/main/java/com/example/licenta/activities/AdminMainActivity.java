package com.example.licenta.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;


import com.example.licenta.R;
import com.example.licenta.activities.fragments.admin.CreateDepartmentFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView tvName;
    CreateDepartmentFragment createDepartmentFragment = new CreateDepartmentFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        init();
        setUpListener();
        setUpFragments();
    }

    private void setUpListener() {
        //set up navigation drawer
        navigationView.setNavigationItemSelectedListener(v->{
            switch (v.getItemId())
            {
                case R.id.item_make_department:
                    openFragment(createDepartmentFragment);
                    break;

            }
            return true;
        });
    }

    //sets up the fragments with the bundles that have the employees in them
    private void setUpFragments() {
        Bundle bundle = new Bundle();


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

    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_admin,fragment);
        fragmentTransaction.commit();
    }



}