package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
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

public class MainActivity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPassword;
    EditText edtPopUpEmail;
    Button btnAuthenticate;
    Button btnPopUpSubmit;
    TextView tvForgotPassword;
    TextView tvCreateAccount;
    String username;
    String password;
    Dialog dForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupButtons();

    }
    //sets up the listeners for buttons
    private void setupButtons() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        tvCreateAccount.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),CreateAccountActivity.class);
            startActivity(intent);
        });
        btnAuthenticate.setOnClickListener(v->{
            extractLoginData();
            if(!username.contains("@") && password.isEmpty()==true)
            {
                Toast.makeText(this, "Please enter your credentials first", Toast.LENGTH_LONG).show();
                return;
            }
            String url = getResources().getString(R.string.url) + "login";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,makeLoginDataJson(),
                    response -> {
                        Employee employee = EmployeeJsonConverter.convertFromJson(response);
                        Intent intent;

                        if(employee.isManager()==false) {
                             intent = new Intent(this,EmployeeMainActivity.class);
                        }
                        else{
                            intent = new Intent(this,ManagerMainActivity.class);
                        }
                        intent.putExtra("employee",employee);
                        startActivity(intent);
                    },
                    error -> {
                        Toast.makeText(this, "Your username and passoword do not match", Toast.LENGTH_LONG).show();
                    });

            if(username.equals("admin")==true && password.equals("admin")==true){
                Intent intent = new Intent(this, AdminMainActivity.class);
                startActivity(intent);
            }
            else {
                requestQueue.add(jsonObjectRequest);
            }

        });

        tvForgotPassword.setOnClickListener(v->{
            dForgotPassword = new Dialog(this);
            dForgotPassword.setContentView(R.layout.dialog_forgot_password);
            dForgotPassword.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
            btnPopUpSubmit = dForgotPassword.findViewById(R.id.btnRecoverPasswordPopUp);
            edtPopUpEmail = dForgotPassword.findViewById(R.id.edtRecoverPasswordPopUp);
            btnPopUpSubmit.setOnClickListener(view->{
                try {
                    String url = getString(R.string.url)+"passwordForgot";
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("email",edtPopUpEmail.getText().toString());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,jsonObject,
                            response -> {Toast.makeText(getApplicationContext(),"Check your email for the new password!",Toast.LENGTH_SHORT).show();},
                            error -> {Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_SHORT).show();});
                    Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            dForgotPassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dForgotPassword.show();
        });
    }

    //gets the inputs and makes it json for backend
    private JSONObject makeLoginDataJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",username);
            jsonObject.put("password",password);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    //retrieves data from inputs
    private void extractLoginData() {
        username = edtUserName.getText().toString();
        password = edtPassword.getText().toString();
    }

    ////Initialize the views
    private void init() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPasswordCreateAccount);
        btnAuthenticate = findViewById(R.id.btnAuthenticare);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
    }
}