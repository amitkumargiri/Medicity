package com.trulloy.bfunx.utility;

import com.trulloy.bfunx.ui.covid19.Covid19Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatesJsonParser {

    private String response;
    private List<Covid19Data> dataList;

    public StatesJsonParser(String response) {
        this.response = response;
        parse();
    }

    private List<Covid19Data> parse() {
        dataList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(response);
            JSONArray states = object.getJSONArray(JsonConstant.STATEWISE);
            for (int i=0; i < states.length(); i++) {
                JSONObject ss = states.getJSONObject(i);
                String state = ss.getString(JsonConstant.STATE);
                int confirmed = ss.getInt(JsonConstant.CONFIRMED);
                int active = ss.getInt(JsonConstant.ACTIVE);
                int recovered = ss.getInt(JsonConstant.RECOVERED);
                int dead = ss.getInt(JsonConstant.DEATHS);
                dataList.add(new Covid19Data(state, confirmed, active, recovered, dead));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public List<Covid19Data> getDataList() {
        return dataList;
    }
}
