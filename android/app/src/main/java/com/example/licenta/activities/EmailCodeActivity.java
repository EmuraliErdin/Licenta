package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class EmailCodeActivity extends AppCompatActivity {
    private Employee employee;
    private EditText edtCode;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_code);

        init();
        setupListener();
    }

    //initialize the views
    private void init() {
        employee = (Employee) getIntent().getSerializableExtra("employee");
        edtCode = findViewById(R.id.edtNumericCode);
        btnSubmit = findViewById(R.id.btnCodeSubmit);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    //sets up the only listener availble
    private void setupListener() {
        btnSubmit.setOnClickListener(v->{
            try {
                String url = getString(R.string.url)+"secondPartCreateAccount";
                JSONObject jsonObject = EmployeeJsonConverter.convertToJson(employee);

                jsonObject.put("code",Integer.parseInt(edtCode.getText().toString()));

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,jsonObject,
                        response -> {
                            Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show();
                            sleep(1000);
                            finish();
                        },
                        error -> {
                            Toast.makeText(this,"Please enter the correct code to register.",Toast.LENGTH_SHORT).show();
                        });
                RequestQueue rq = Volley.newRequestQueue(this);
                rq.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }


    //stops the activity for 'milis' miliseconds
    private void sleep(int milis) {
        try {
            Thread.sleep(milis);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

}