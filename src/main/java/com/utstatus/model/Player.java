package com.utstatus.model;

import static com.utstatus.server.QueryParser.stripColors;

/**
 *
 * @author dcnorris
 */
public class Player {

    private int ping;
    private int score;
    private String name;
    private boolean isBot;

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = stripColors(name);
    }

    public boolean isIsBot() {
        return ping == 0;
    }

}
