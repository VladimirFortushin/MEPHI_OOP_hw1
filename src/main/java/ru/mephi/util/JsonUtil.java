package ru.mephi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T jsonToPojo(String json, Class<T> classOfT) throws Exception {
        return gson.fromJson(json, classOfT);
    }

    public static <T> String pojoToJson(T obj) {
        return gson.toJson(obj);
    }

}
