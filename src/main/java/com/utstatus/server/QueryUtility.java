/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dcnorris
 */
public class QueryUtility {

    public static String getRawStatus() throws Exception {
        ServerQuery query = new ServerQuery();
        query.send("getstatus");
        String response = query.getResponse();
        response = QueryParser.prepareParsedResponse(response);
        return response;
    }

    public static String getServerInfo() throws Exception {
        ServerQuery query = new ServerQuery();
        query.send("getinfo");
        String response = query.getResponse();
        response = QueryParser.prepareParsedResponse(response);
        return response;
    }

    public static Map getInfoMap(String info) throws Exception {
        Map infoMap = getServerInfo(info);
        return infoMap;
    }

    public static Map getStatusMap(String status) throws Exception {
        Map statusMap = getServerInfo(status);
        return statusMap;
    }

    private static Map getServerInfo(String resp) {
        if (!resp.equals("")) {
            resp = QueryParser.parseInfoResponse(resp);
            resp = resp.substring(1);
            String[] foo = resp.split("\\\\");
            ArrayList<String> keys = new ArrayList();
            ArrayList<String> vals = new ArrayList();
            boolean direction = true;
            for (int i = 0; i < foo.length; i++) {
                if (direction) {
                    keys.add(foo[i]);
                } else {
                    vals.add(foo[i]);
                }
                direction = (direction) ? false : true;
            }
            Map<String, String> map = new HashMap();
            if (vals.size() > 0) {
                for (int i = 0; i < keys.size(); i++) {
                    String key = keys.get(i);
                    String val = vals.get(i);
                    map.put(key, val);
                }
            }
            return map;
        } else {
            return null;
        }
    }
}
