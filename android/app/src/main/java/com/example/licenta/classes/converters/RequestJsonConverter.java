package com.example.licenta.classes.converters;

import com.example.licenta.classes.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class RequestJsonConverter {


    public static Request convertFromJson(JSONObject jsonObject) {

        if(jsonObject==null)
        {
            return null;
        }
        Request request = new Request();
        try {
            request.setId(jsonObject.getString("id"));

            LocalDateTime createDate = LocalDateTime.parse(jsonObject.getString("createDate"));
            request.setCreateDate(createDate);

            LocalDate requestDate = LocalDate.parse(jsonObject.getString("requestDate"));
            request.setRequestDate(requestDate);

            request.setReason(jsonObject.getString("reason"));

            request.setStatus(jsonObject.getString("status"));
            request.setEmployeeId(jsonObject.getString("employeeId"));

            request.setNumberOfHours(jsonObject.getInt("numberOfHours"));

            request.setType(jsonObject.getString("type"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return request;
    }


    public static JSONObject convertToJson(Request request) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reason",request.getReason());
            jsonObject.put("requestDate",request.getRequestDate());
            jsonObject.put("createDate",request.getCreateDate());
            jsonObject.put("status",request.getStatus());
            jsonObject.put("employeeId",request.getEmployeeId());
            jsonObject.put("numberOfHours",request.getNumberOfHours());
            jsonObject.put("type",request.getType());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Request> convertListFromJson(JSONArray jsonArray) {
        LinkedList<Request> requestList = new LinkedList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                requestList.add(convertFromJson(jsonArray.getJSONObject(i)));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestList;
    }
}
