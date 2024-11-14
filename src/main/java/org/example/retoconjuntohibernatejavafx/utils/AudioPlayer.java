package org.example.retoconjuntohibernatejavafx.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Clase `AudioPlayer` para reproducir y detener pistas de audio.
 * Utiliza `MediaPlayer` de JavaFX para la reproducción de archivos de audio.
 */
public class AudioPlayer {

    private MediaPlayer mediaPlayer;

    /**
     * Reproduce un archivo de audio especificado.
     * Verifica si el archivo existe en la ruta antes de reproducir.
     *
     * @param audioFilePath La ruta relativa del archivo de audio dentro de los recursos del proyecto.
     */
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

    /**
     * Detiene la reproducción de la pista de audio y libera los recursos asociados.
     */
    public void stopSoundtrack() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}

