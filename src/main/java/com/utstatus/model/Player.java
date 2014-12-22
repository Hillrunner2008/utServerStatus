package com.utstatus.model;

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
        this.name = name;
    }

    public boolean isIsBot() {
        return isBot;
    }

    public void setIsBot(boolean isBot) {
        this.isBot = isBot;
    }

}
