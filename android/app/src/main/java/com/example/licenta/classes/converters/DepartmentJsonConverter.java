package com.example.licenta.classes.converters;

import com.example.licenta.classes.Department;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class DepartmentJsonConverter {

    public static Department convertFromJson(JSONObject jsonObject) {

        if(jsonObject==null)
        {
            return null;
        }
        Department department = new Department();
        try {
            department.setId(jsonObject.getString("id"));

            department.setTitle(jsonObject.getString("title"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return department;
    }


    public static JSONObject convertToJson(Department department) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title",department.getTitle());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Department> convertListFromJson(JSONArray jsonArray) {
        LinkedList<Department> requestList = new LinkedList<>();
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
