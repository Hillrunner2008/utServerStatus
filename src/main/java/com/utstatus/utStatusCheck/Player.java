/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.utStatusCheck;

/**
 *
 * @author dcnorris
 */
public class Player {

    private int ping;
    private int score;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
