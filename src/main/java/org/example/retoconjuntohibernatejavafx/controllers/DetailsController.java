package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.dao.PeliculaDAO;
import org.example.retoconjuntohibernatejavafx.utils.AudioPlayer;
import org.example.retoconjuntohibernatejavafx.utils.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.CopiaDAO;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de detalles de la copia de una película.
 * Permite visualizar la información de la copia, actualizar sus datos,
 * eliminar la copia y reproducir una pista de audio asociada.
 */
public class DetailsController implements Initializable {

    @FXML
    private TextField generopeli;
    @FXML
    private TextField aniopeli;
    @FXML
    private TextField clasifipeli;
    @FXML
    private TextField estadocopia;
    @FXML
    private TextField directorpeli;
    @FXML
    private TextField duracionpeli;
    @FXML
    private TextField titulopeli;
    @FXML
    private Label idpeli;
    @FXML
    private Label idcopia;
    @FXML
    private Spinner<Integer> cantidadcopia;
    @FXML
    private ComboBox<String> formatocopia;

    private AudioPlayer audioPlayer;

    /**
     * Guarda los cambios realizados en la copia actual, incluyendo
     * el formato y la cantidad, y detiene el audio al salir.
     *
     * @param actionEvent El evento de acción del botón de guardar.
     */
    @FXML
    public void savecopia(ActionEvent actionEvent) {
        String nuevoFormato = formatocopia.getValue();
        int nuevaCantidad = cantidadcopia.getValue();

        CurrentSession.currentCopia.setFormato(nuevoFormato);
        CurrentSession.currentCopia.setCantidad(nuevaCantidad);

        new CopiaDAO(HibernateUtils.getSessionFactory()).update(CurrentSession.currentCopia);
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");
        stopAudio();
    }

    /**
     * Cierra la vista de detalles de la copia y detiene el audio.
     *
     * @param actionEvent El evento de acción del botón de salida.
     */
    @FXML
    public void exitcopia(ActionEvent actionEvent) {
        stopAudio();  // Detiene el audio al salir
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");
    }

    /**
     * Elimina la copia actual tras confirmar la acción con el usuario y detiene el audio.
     *
     * @param actionEvent El evento de acción del botón de eliminar.
     */
    @FXML
    public void deletecopia(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Copia");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar esta copia?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            new CopiaDAO(HibernateUtils.getSessionFactory()).delete(CurrentSession.currentCopia);
        }
        stopAudio();
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");
    }

    /**
     * Reproduce la pista de audio asociada a la copia actual si está disponible.
     */
    private void playSoundtrack() {
        String audioFile = CurrentSession.currentCopia.getAudio();
        if (audioFile != null) {
            audioPlayer.playSoundtrack(audioFile);
        } else {
            System.out.println("No hay pista de audio asignada a esta copia.");
        }
    }

    /**
     * Detiene la reproducción de la pista de audio si está en reproducción.
     */
    public void stopAudio() {
        if (audioPlayer != null) {
            audioPlayer.stopSoundtrack();
        }
    }

    /**
     * Inicializa la vista de detalles, configurando los campos de texto y otros elementos
     * de la interfaz con los datos de la copia actual y comenzando la reproducción del audio.
     *
     * @param url La URL utilizada para resolver rutas relativas.
     * @param resourceBundle Los recursos que se utilizan para localizar los elementos de la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titulopeli.setText(CurrentSession.currentCopia.getPelicula().getTitulo());
        generopeli.setText(CurrentSession.currentCopia.getPelicula().getGenero());
        aniopeli.setText(CurrentSession.currentCopia.getPelicula().getAnio().toString());
        clasifipeli.setText(CurrentSession.currentCopia.getPelicula().getClasificacion());
        duracionpeli.setText(CurrentSession.currentCopia.getPelicula().getDuracion().toString());
        directorpeli.setText(CurrentSession.currentCopia.getPelicula().getDirector());
        estadocopia.setText(CurrentSession.currentCopia.getEstado());
        idpeli.setText("ID DE LA PELICULA: " + CurrentSession.currentCopia.getPelicula().getId().toString());
        idcopia.setText("ID DE LA COPIA: " + CurrentSession.currentCopia.getId().toString());

        formatocopia.getItems().setAll("DVD", "Blu-ray", "Digital", "VHS");
        formatocopia.setValue(CurrentSession.currentCopia.getFormato());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, CurrentSession.currentCopia.getCantidad());
        cantidadcopia.setValueFactory(valueFactory);

        audioPlayer = new AudioPlayer();
        playSoundtrack();
    }
}
