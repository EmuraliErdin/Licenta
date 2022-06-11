package com.example.licenta.classes.converters;
import com.example.licenta.classes.Prize;

import org.json.JSONException;
import org.json.JSONObject;

public class PrizeJsonConverter {

    public static Prize convertFromJson(JSONObject jsonObject) {
        if(jsonObject==null)
        {
            return null;
        }
        Prize prize = new Prize();
        try {
            prize.setId(jsonObject.getString("id"));
            prize.setTitle(jsonObject.getString("name"));
            prize.setDescription(jsonObject.getString("description"));
            prize.setNecessaryLevel(jsonObject.getInt("necessaryLevel"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return prize;
    }


    public static JSONObject convertToJson(Prize prize) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",prize.getTitle());
            jsonObject.put("necessaryLevel", prize.getNecessaryLevel());
            jsonObject.put("description", prize.getDescription());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
