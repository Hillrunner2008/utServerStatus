/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.model;

/**
 *
 * @author dcnorris
 */
public enum ServerType {

    TDM(3),
    TS(4),
    CTF(7),
    FTL(5),
    FFA(0),
    BOMB(8),
    CAPTURE_HOLD(6);

    private final int value;

    private ServerType(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }

    public static ServerType findByValue(int value) {
        for (ServerType st : values()) {
            if (st.getValue() == value) {
                return st;
            }
        }
        throw new IllegalArgumentException();
    }
}
