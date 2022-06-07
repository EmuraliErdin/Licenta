package com.example.licenta.classes.converters;

import com.example.licenta.classes.Employee;
import com.example.licenta.classes.Role;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class RoleJsonConverter {

    public static JSONObject convertToJson(Role role) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type",role.getType());
            jsonObject.put("from",role.getFrom());
            jsonObject.put("givenTo",role.getGivenTo());
            jsonObject.put("startDate",role.getStartDate());
            jsonObject.put("endDate",role.getEndDate());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Role convertFromJson(JSONObject jsonObject) {
        if(jsonObject==null)
        {
            return null;
        }

        Role role = new Role();
        try {
            role.setId(jsonObject.getString("id"));
            role.setFrom(jsonObject.getString("from"));
            role.setGivenTo(jsonObject.getString("givenTo"));

            LocalDate endDate = LocalDate.parse(jsonObject.getString("endDate"));
            role.setEndDate(endDate);

            LocalDate startDate = LocalDate.parse(jsonObject.getString("startDate"));
            role.setStartDate(startDate);

            role.setType(jsonObject.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return role;
    }
}
