package com.xiamubobby.justlauncher.utility;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.xiamubobby.justlauncher.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MuSTERMIND on 2015/4/5.
 */
public class RawJsonReader {
    protected Context context;
    protected Gson gson;
    protected JsonReader jsonReader;
    private RawJsonReader(Context context) {
        this.context = context;
    }
    static private RawJsonReader instance;
    public static RawJsonReader construct(Context context) {
        if (instance == null) {
            instance = new RawJsonReader(context);
            instance.gson = new Gson();
        }
        else {
            instance.context = context;
        }
        return instance;
    }
    public RawJsonReader consume(int rawResourceId) throws UnsupportedEncodingException {
        InputStream inputStream = context.getResources().openRawResource(rawResourceId);
        jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        return this;
    }
    public String productString() {
        return jsonReader.toString();
        //return jsonReader.toString();
    }
}
