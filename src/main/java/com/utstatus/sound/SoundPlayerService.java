package com.utstatus.sound;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class SoundPlayerService {

    public SoundPlayer getAudioPlayer() throws UnsupportedAudioFileException, IOException {
        URL url = this.getClass().getClassLoader().getResource("ding.wav");
        AudioInputStream audioIn = getAudioInputStream(url);
        SoundPlayer player = new SoundPlayer(audioIn);
        return player;
    }
}
