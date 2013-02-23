package com.utstatus.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author dcnorris
 */
public final class xmlWriter {

    private BufferedWriter outputStream = null;
    private File file;

    public void savePlayerData(String ip, String playerName, int port, String path) throws IOException {
        if (file
                == null) {
            file = new File(System.getProperty("user.dir") + "/utserverstatus_data.xml");
        }
        outputStream = new BufferedWriter(new FileWriter(file));

        outputStream.write(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        outputStream.write(
                "<!--\n");
        outputStream.write(
                "Created on : September 27, 2011, 4:39 PM\n");
        outputStream.write(
                "Author     : dcnorris\n");
        outputStream.write(
                "-->\n");
        outputStream.write(
                "<Servers><SERVER_ASSOCIATIONS>1\n");
        outputStream.write(
                "<ip>" + ip + " </ip>\n");
        outputStream.write(
                "<port>" + port + " </port> \n");
        outputStream.write(
                "<name>" + playerName + " </name>\n");
        outputStream.write(
                "<exePath>" + path + " </exePath>");
        outputStream.write(
                "</SERVER_ASSOCIATIONS>\n");
        outputStream.write(
                "</Servers>");
        outputStream.close();
    }
}
