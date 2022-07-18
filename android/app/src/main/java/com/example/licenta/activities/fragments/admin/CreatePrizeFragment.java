package com.example.licenta.activities.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Prize;
import com.example.licenta.classes.converters.PrizeJsonConverter;

public class CreatePrizeFragment extends Fragment {
    private EditText edtPrizeTitle;
    private EditText edtPrizeDescription;
    private EditText edtNecessaryLevel;
    private Button btnSubmit;

    public CreatePrizeFragment() { }

    public static CreatePrizeFragment newInstance(String param1, String param2) {
        CreatePrizeFragment fragment = new CreatePrizeFragment();
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
        return inflater.inflate(R.layout.fragment_create_prize, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        setUpListener();
    }

    private void setUpListener() {
        btnSubmit.setOnClickListener(v->{
            if(validate()) {
                Prize prize = generatePrize();
                String url = getString(R.string.url)+"prizes";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                        PrizeJsonConverter.convertToJson(prize),
                        response -> {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        },
                        error -> {
                            Toast.makeText(getContext(),"An error occurred",Toast.LENGTH_SHORT).show();
                        });
                Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
            }

        });

    }

    private Prize generatePrize() {
        int necessaryLevel = Integer.parseInt(edtNecessaryLevel.getText().toString());
        String title = edtPrizeTitle.getText().toString();
        String description = edtPrizeDescription.getText().toString();
        return new Prize(necessaryLevel, title, description);
    }

    private boolean validate() {
        try {
            if(edtPrizeTitle.getText().toString().length()<=3) {
                Toast.makeText(getContext(),"The title must contain something", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(edtPrizeDescription.getText().toString().length()>=25) {
                Toast.makeText(getContext(),"The description should be under 25 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(Integer.parseInt(edtNecessaryLevel.getText().toString()) <=1) {
                Toast.makeText(getContext(), "The level must be above 1", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void init(){
        edtPrizeTitle = getView().findViewById(R.id.edtTitleOfPrize);
        edtPrizeDescription = getView().findViewById(R.id.edtPrizeDescription);
        edtNecessaryLevel = getView().findViewById(R.id.edtNecessaryLevel);
        btnSubmit = getView().findViewById(R.id.btnSubmitDepartmentNamee);
    }
}