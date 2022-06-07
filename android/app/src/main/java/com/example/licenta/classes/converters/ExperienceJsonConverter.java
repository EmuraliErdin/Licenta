package com.example.licenta.classes.converters;

import com.example.licenta.classes.Experience;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ExperienceJsonConverter {

    public static Experience convertFromJson(JSONObject jsonObject) {

        if(jsonObject==null)
        {
            return null;
        }
        Experience experience = new Experience();
        try {
            experience.setId(jsonObject.getString("id"));

            LocalDate createDate = LocalDate.parse(jsonObject.getString("createDate"));
            experience.setCreateDate(createDate);

            experience.setEmployeeId(jsonObject.getString("employeeId"));

            experience.setReason(jsonObject.getString("reason"));

            experience.setXp(jsonObject.getInt("xp"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return experience;
    }

    public static JSONObject convertToJson(Experience experience) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reason",experience.getReason());
            jsonObject.put("createDate",experience.getCreateDate());
            jsonObject.put("id",experience.getId());
            jsonObject.put("employeeId",experience.getEmployeeId());
            jsonObject.put("xp",experience.getXp());

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Experience> convertListFromJson(JSONArray jsonArray) {
        LinkedList<Experience> experienceList = new LinkedList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                experienceList.add(convertFromJson(jsonArray.getJSONObject(i)));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return experienceList;
    }
}
