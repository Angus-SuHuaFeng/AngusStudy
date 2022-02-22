package com.angus.flume.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

/**
 * @author Angus
 */
public class JsonUtils {

    public static boolean isValidate(String log) {
        try{
            JSON.parse(log);
            return true;
        }catch (JSONException e){
            return false;
        }
    }
}
