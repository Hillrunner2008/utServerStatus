package com.utstatus.sound;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundPlayer extends Thread {

    private AudioInputStream is = null;
    private final int EXTERNAL_BUFFER_SIZE = 524288;
    private static final Logger logger = LoggerFactory.getLogger(SoundPlayer.class);

    public SoundPlayer(AudioInputStream input) {
        is = input;
    }

    @Override
    public void run() {
        AudioInputStream soundFile = is;
        if (soundFile == null) {
            System.out.println("Wave file not found.");
            return;
        }

        AudioInputStream audioInputStream = is;
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) {
            logger.error(e.getMessage(), e);
            return;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }

        if (auline.isControlSupported(FloatControl.Type.PAN)) {
            FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
        }

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    auline.write(abData, 0, nBytesRead);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            auline.drain();
            auline.close();
        }
    }
}
