package com.utstatus;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import static com.google.common.io.Resources.asCharSource;
import com.utstatus.model.Player;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dcnorris
 */
public class PlayerListParserTest {

    @Test
    public void parseRawResponse() throws IOException {
        URL url = PlayerListParserTest.class.getClassLoader().getResource("rawResponse.txt");
        CharSource rawResponse = asCharSource(url, Charsets.UTF_8);

        List<Player> players = getPlayerList(rawResponse.readLines());
        assertEquals(players.size(), 26);
    }

    private List<Player> getPlayerList(List<String> lines) {
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                continue;
            }
            String line = lines.get(i);
            String[] lineSplit = breakLines(line);
            Player player = new Player();
            player.setScore(parseInt(lineSplit[0]));
            player.setPing(parseInt(lineSplit[1]));
            player.setName(lineSplit[2]);
            players.add(player);
        }
        return players;
    }

    private String[] breakLines(String line) {
        String[] thisLine = new String[3];
        String scorePing = line.substring(0, line.indexOf('\"'));
        String[] tempSplit = scorePing.split(" ");
        thisLine[0] = tempSplit[0]; // Score
        thisLine[1] = tempSplit[1]; // Ping
        thisLine[2] = line.substring(line.indexOf('\"') + 1, line.lastIndexOf('\"'));
        return thisLine;
    }
}
