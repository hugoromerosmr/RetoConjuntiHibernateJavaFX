package org.example.retoconjuntohibernatejavafx.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioPlayer {

    private MediaPlayer mediaPlayer;

    public void playSoundtrack(String audioFilePath) {
        File audioFile = new File("src/main/resources/org/example/retoconjuntohibernatejavafx/sound/" + audioFilePath); // Ajusta la ruta base si es necesario
        if (audioFile.exists()) {
            Media media = new Media(audioFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } else {
            System.out.println("Archivo de audio no encontrado: " + audioFilePath);
        }
    }


    public void stopSoundtrack() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}
