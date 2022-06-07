package com.example.licenta.classes.converters;

import com.example.licenta.classes.Issue;

import org.json.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class IssueJsonConverter {

    public static Issue convertFromJson(JSONObject jsonObject) {

        if(jsonObject==null)
        {
            return null;
        }
        Issue issue = new Issue();
        try {
            issue.setId(jsonObject.getString("id"));

            LocalDate createDate = LocalDate.parse(jsonObject.getString("createDate"));
            issue.setCreateDate(createDate);

            issue.setStatus(jsonObject.getString("status"));

            issue.setReason(jsonObject.getString("reason"));

            issue.setPriorityLevel(jsonObject.getString("priorityLevel"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return issue;
    }

    public static JSONObject convertToJson(Issue issue) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reason",issue.getReason());
            jsonObject.put("createDate",issue.getCreateDate());
            jsonObject.put("status",issue.getStatus());
            jsonObject.put("priorityLevel",issue.getPriorityLevel());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Issue> convertListFromJson(JSONArray jsonArray) {
        LinkedList<Issue> issuesList = new LinkedList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                issuesList.add(convertFromJson(jsonArray.getJSONObject(i)));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return issuesList;
    }
}
