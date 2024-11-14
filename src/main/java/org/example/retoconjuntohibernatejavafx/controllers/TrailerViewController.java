package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de tráiler de película.
 * Carga y reproduce el tráiler de YouTube de una película seleccionada.
 */
public class TrailerViewController implements Initializable {
    @javafx.fxml.FXML
    private Button buttonclose;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private WebView webview;

    /**
     * Inicializa la vista del tráiler y carga el video de YouTube
     * asociado a la película actual en `CurrentSession`.
     *
     * @param url            La URL utilizada para resolver rutas relativas.
     * @param resourceBundle Los recursos que se utilizan para localizar los elementos de la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (CurrentSession.currentPelicula.getVideoId() != null && !CurrentSession.currentPelicula.getVideoId().isEmpty()) {
            String videoUrl = "https://www.youtube.com/embed/" + CurrentSession.currentPelicula.getVideoId() + "?autoplay=1&rel=0";
            WebEngine webEngine = webview.getEngine();
            webEngine.load(videoUrl);
        }
    }

    /**
     * Cierra la vista de tráiler y regresa a la vista de administrador.
     * Detiene el video antes de cerrar.
     *
     * @param actionEvent Evento de acción para el botón de cerrar.
     */
    @javafx.fxml.FXML
    public void close(ActionEvent actionEvent) {
        stopVideo();
        HelloApplication.loadFXML("view/admin-view.fxml", "Vista de Administrador");
    }

    /**
     * Detiene la reproducción del video limpiando el contenido del `WebView`.
     */
    private void stopVideo() {
        WebEngine webEngine = webview.getEngine();
        webEngine.loadContent("");
    }
}
