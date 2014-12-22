package com.utstatus.server;

import com.google.common.base.Splitter;
import com.utstatus.model.Player;
import java.util.ArrayList;
import java.util.List;
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

    public static List<Player> getPlayerList(String rawReponse) {
        List<Player> players = new ArrayList<>();
        try {
            List<String> lines = Splitter.on("\n").omitEmptyStrings().trimResults().splitToList(rawReponse);

            for (int i = 0; i < lines.size(); i++) {
                if (i == 0) {
                    continue;
                }
                String line = lines.get(i);
                String[] lineSplit = breakLines(line);
                Player player = new Player();
                player.setScore(Integer.parseInt(lineSplit[0]));
                player.setPing(Integer.parseInt(lineSplit[1]));
                String playName = lineSplit[2];
                if (player.getPing() == 0) {
                    playName = playName + " (BOT)";
                }
                player.setName(playName);
                players.add(player);
            }
        } catch (Exception ex) {
            //do nothing
        }
        return players;
    }

    private static String[] breakLines(String line) {
        String[] thisLine = new String[3];
        String scorePing = line.substring(0, line.indexOf('\"'));
        String[] tempSplit = scorePing.split(" ");
        thisLine[0] = tempSplit[0]; // Score
        thisLine[1] = tempSplit[1]; // Ping
        thisLine[2] = line.substring(line.indexOf('\"') + 1, line.lastIndexOf('\"'));
        return thisLine;
    }
}
