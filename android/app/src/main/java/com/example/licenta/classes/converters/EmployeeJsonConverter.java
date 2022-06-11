package com.example.licenta.classes.converters;

import com.example.licenta.classes.Department;
import com.example.licenta.classes.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class EmployeeJsonConverter {
    public static JSONObject convertToJson(Employee employee)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName",employee.getFirstName());
            jsonObject.put("lastName",employee.getLastName());
            jsonObject.put("password",employee.getPassword());
            jsonObject.put("email",employee.getEmail());
            jsonObject.put("departmentId",employee.getDepartmentId());
            jsonObject.put("isManager",employee.isManager());
            jsonObject.put("level", employee.getLevel());
            jsonObject.put("experience", employee.getExperience());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Employee convertFromJson(JSONObject jsonObject)
    {
        if(jsonObject==null)
        {
            return null;
        }

        Employee employee = new Employee();
        try {
            employee.setId(jsonObject.getString("id"));
            employee.setFirstName(jsonObject.getString("firstName"));
            employee.setLastName(jsonObject.getString("lastName"));
            employee.setEmail(jsonObject.getString("email"));
            employee.setDepartmentId(jsonObject.getString("departmentId"));
            employee.setIsManager(jsonObject.getBoolean("isManager"));
            employee.setLevel(jsonObject.getInt("level"));
            employee.setExperience(jsonObject.getInt("experience"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return employee;
    }

    public static List<Employee> convertListFromJson(JSONArray jsonArray) {
        LinkedList<Employee> employeeList = new LinkedList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                employeeList.add(convertFromJson(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return employeeList;
    }


}
