package com.trulloy.bfunx.utility;


import com.google.android.gms.common.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Amit Kumar Giri (allyamit@gmail.com)
 */
public class Covid19JsonObjectModel {

    public boolean parseJsonData(String response) {
        try {
            JSONObject data = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}
