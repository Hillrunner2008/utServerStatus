/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.persistence;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.utstatus.model.Configuration;
import java.io.Reader;

/**
 *
 * @author dcnorris
 */
public class ConfigurationParser {

    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();

    public static Configuration fromJson(Reader reader) throws JsonSyntaxException, JsonIOException {
        return gson.fromJson(reader, Configuration.class);
    }

    public static String toJson(Configuration config) {
        return gson.toJson(config);
    }
}
