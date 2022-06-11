package com.example.licenta.activities.fragments.admin;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.licenta.R;
import com.example.licenta.classes.Issue;
import com.example.licenta.classes.adapters.IssueAdapter;
import com.example.licenta.classes.converters.IssueJsonConverter;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.List;

public class IssueListFragment extends Fragment implements IssueAdapter.OnNoteListener {
    private List<Issue> issueList;
    RecyclerView rvIssues;

    public IssueListFragment() {
    }

    public static IssueListFragment newInstance(String param1, String param2) {
        IssueListFragment fragment = new IssueListFragment();
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
        return inflater.inflate(R.layout.fragment_issue_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void getIssues() {
        String url = getString(R.string.url)+"issues";
        JsonArrayRequest obj = new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    issueList = IssueJsonConverter.convertListFromJson(response);
                    IssueAdapter issueAdapter = new IssueAdapter(getContext(), issueList, this);
                    rvIssues.setAdapter(issueAdapter);
                    rvIssues.setLayoutManager(new LinearLayoutManager(getContext()));
                },
                error -> {
                    Toast.makeText(getContext(),"An error occurred",Toast.LENGTH_SHORT);
                });
        Volley.newRequestQueue(getContext()).add(obj);
    }

    private void init() {
        rvIssues = getView().findViewById(R.id.rvIssues);
        getIssues();
    }

    @Override
    public void onIssueClick(int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Action");
        String[] options;
        options = new String[]{"Mark this as solved"};
        Issue issue = issueList.get(position);
        if(issue.getStatus().equals("PENDING"))
        {
            dialog.setItems(options,
                    (d,w)->{
                        switch (w){
                            case 0: {
                                String url = getString(R.string.url)+"/issues/"+issue.getId();
                                JSONObject jsonObject = new JSONObject();
                                issue.setStatus("SOLVED");

                                try{
                                    jsonObject.put("status",issue.getStatus());
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                                JsonObjectRequest obj = new JsonObjectRequest(Request.Method.PATCH,url,jsonObject,
                                        response -> {
                                            Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT);
                                        },
                                        error -> {
                                            Toast.makeText(getContext(),"An error occurred",Toast.LENGTH_SHORT);
                                        });
                                Volley.newRequestQueue(getContext()).add(obj);
                                getIssues();
                                break;
                            }
                        }
                    }).show();
        }

    }
}