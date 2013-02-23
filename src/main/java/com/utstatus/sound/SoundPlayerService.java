package com.utstatus.sound;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class SoundPlayerService {

    //To Do: Move this to a more appropriate place
    public SoundPlayer getAudioPlayer() throws UnsupportedAudioFileException, IOException {
        URL url = this.getClass().getClassLoader().getResource("ding.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
        SoundPlayer player = new SoundPlayer(audioIn);
        return player;
    }
}
