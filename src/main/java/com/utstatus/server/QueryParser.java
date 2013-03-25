/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dcnorris
 */
public class QueryParser {

    public static String stripPrintCommands(String input) {
        Pattern r = Pattern.compile("....print\n");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }

    public static String parseStatusResponse(String resp) {
        Pattern r = Pattern.compile("....statusResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        return resp;
    }

    public static String parseInfoResponse(String resp) {
        Pattern r = Pattern.compile("....infoResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        return resp;
    }

    public static String prepareParsedResponse(String resp) {
        String parsedResponse = parseStatusResponse(resp);
        String strippedResponse = stripPrintCommands(parsedResponse);
        return strippedResponse;
    }
}
