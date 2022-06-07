package com.example.licenta.classes.converters;

import com.example.licenta.classes.Log;
import com.example.licenta.classes.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class LogJsonConverter {

    public static Log convertFromJson(JSONObject jsonObject) {

        if(jsonObject==null)
        {
            return null;
        }
        Log log = new Log();
        try {
            log.setId(jsonObject.getString("id"));

            LocalDate createDate = LocalDate.parse(jsonObject.getString("createDate"));
            log.setCreateDate(createDate);

            log.setAction(jsonObject.getString("action"));

            log.setEmployeeID(jsonObject.getString("employeeId"));


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return log;
    }


    public static JSONObject convertToJson(Log log) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("action",log.getAction());
            jsonObject.put("createDate",log.getCreateDate());
            jsonObject.put("employeeId",log.getEmployeeID());
            jsonObject.put("action",log.getAction());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Log> convertListFromJson(JSONArray jsonArray) {
        LinkedList<Log> logList = new LinkedList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                logList.add(convertFromJson(jsonArray.getJSONObject(i)));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return logList;
    }

}
