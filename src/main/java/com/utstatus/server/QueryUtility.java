package com.utstatus.server;

import com.utstatus.model.Configuration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dcnorris
 */
public class QueryUtility {

    private Configuration config;

    public QueryUtility(Configuration config) {
        this.config = config;
    }

    public String getMasterList() throws Exception {
        Configuration masterConfig = new Configuration();
        masterConfig.setIp("178.32.60.13");
        masterConfig.setPort(27950);
        ServerQuery query = new ServerQuery(masterConfig);
        query.send("getservers 68 full empty");
        String response = query.getResponse();
        return response;
    }

    public String getRawStatus() throws Exception {
        ServerQuery query = new ServerQuery(config);
        query.send("getstatus");
        String response = query.getResponse();
        response = QueryParser.prepareParsedResponse(response);
        return response;
    }

    public String getServerInfo() throws Exception {
        ServerQuery query = new ServerQuery(config);
        query.send("getinfo");
        String response = QueryParser.prepareParsedResponse(query.getResponse());
        return response;
    }

    public Map getInfoMap(String info) throws Exception {
        Map infoMap = getServerInfo(info);
        return infoMap;
    }

    public Map getStatusMap(String status) throws Exception {
        Map statusMap = getServerInfo(status);
        return statusMap;
    }

    private Map getServerInfo(String resp) {
        if (!resp.equals("")) {
            resp = QueryParser.parseInfoResponse(resp);
            resp = resp.substring(1);
            String[] attributes = resp.split("\\\\");
            List<String> keys = new ArrayList();
            List<String> vals = new ArrayList();
            boolean direction = true;
            for (String attribute : attributes) {
                if (direction) {
                    keys.add(attribute);
                } else {
                    vals.add(attribute);
                }
                direction = !(direction);
            }
            Map<String, String> map = new HashMap<>();
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
