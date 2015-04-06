package com.xiamubobby.justlauncher.view;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.xiamubobby.justlauncher.utility.RawJsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by MuSTERMIND on 2015/4/5.
 */
public class RawJustCauseJsonReader{

    private Context context;
    private Gson gson;
    private JsonReader jsonReader;
    public RawJustCauseJsonReader(Context context) {
        this.context = context;
    }
    public static RawJustCauseJsonReader create(Context context) {
        RawJustCauseJsonReader returnInstance =  new RawJustCauseJsonReader(context);
        returnInstance.gson = new Gson();
        return returnInstance;
    }
    public RawJustCauseJsonReader consume(int rawResourceId) throws UnsupportedEncodingException {
        InputStream inputStream = context.getResources().openRawResource(rawResourceId);
        jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        return this;
    }
    public String productString() {
        return jsonReader.toString();
        //return jsonReader.toString();
    }

    public <T>  T productAs(Class<T> tClass) throws IOException, IllegalAccessException, InstantiationException {
        T t = tClass.newInstance();
        JustCauseBean justCauseBean = gson.fromJson(jsonReader, tClass);
        return (T) justCauseBean;
    }
}
