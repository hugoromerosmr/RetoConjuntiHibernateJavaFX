package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.utils.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.CopiaDAO;
import org.example.retoconjuntohibernatejavafx.dao.PeliculaDAO;
import org.example.retoconjuntohibernatejavafx.models.Copia;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;
import org.example.retoconjuntohibernatejavafx.models.Pelicula;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de creación de una nueva copia de película.
 * Permite seleccionar la película, establecer la cantidad, formato, estado,
 * y asignar un archivo de audio para la copia.
 */
public class NewcopyController implements Initializable {

    @FXML
    private BorderPane border;
    @FXML
    private ScrollPane scroll1;
    @FXML
    private VBox scroll;
    @FXML
    private ComboBox<String> estadocopia;
    @FXML
    private ComboBox<String> formatocopia;
    @FXML
    private Spinner<Integer> cantidadcopia;
    @FXML
    private ComboBox<Pelicula> peliculasComboBox;
    @FXML
    private Button buttonsave;
    @FXML
    private ImageView imgcopia;
    @FXML
    private Button buttonexit;
    @FXML
    private Button buttonSelectAudio;

    private PeliculaDAO peliculaDAO;
    private CopiaDAO copiaDAO;
    private File selectedAudioFile; // Archivo de audio seleccionado

    /**
     * Guarda la copia de película creada, asignando sus valores y guardando en la base de datos.
     * Muestra una alerta si algún campo requerido no está completo.
     *
     * @param actionEvent Evento de acción del botón de guardar copia.
     */
    @FXML
    public void savecopia(ActionEvent actionEvent) {
        Pelicula selectedPelicula = peliculasComboBox.getValue();
        String formato = formatocopia.getValue();
        String estado = estadocopia.getValue();
        Integer cantidad = cantidadcopia.getValue();

        if (selectedPelicula == null || formato == null || estado == null || cantidad == null) {
            showAlert("Error", "Todos los campos deben estar completos.", Alert.AlertType.ERROR);
            return;
        }

        Copia copia = new Copia();
        copia.setPelicula(selectedPelicula);
        copia.setUsuario(CurrentSession.currentUser);
        copia.setFormato(formato);
        copia.setEstado(estado);
        copia.setCantidad(cantidad);

        if (selectedAudioFile != null) {
            copia.setAudio(selectedAudioFile.getName());
        } else {
            System.out.println("No se seleccionó un archivo de audio.");
        }

        new CopiaDAO(HibernateUtils.getSessionFactory()).save(copia);
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");
    }

    /**
     * Cierra la vista de creación de copia.
     *
     * @param actionEvent Evento de acción del botón de salida.
     */
    @FXML
    public void exitcopia(ActionEvent actionEvent) {
        border.getScene().getWindow().hide();
    }

    /**
     * Abre un explorador de archivos para seleccionar un archivo de audio y lo asigna a la copia.
     * Filtra archivos para mostrar solo formatos de audio compatibles (.mp3 y .wav).
     */
    @FXML
    public void selectAudioFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo de audio");

        File initialDirectory = new File("src/main/resources/org/example/retoconjuntohibernatejavafx/sound");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Audio", "*.mp3", "*.wav")
        );

        Stage stage = (Stage) buttonSelectAudio.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedAudioFile = file;
            System.out.println("Archivo de audio seleccionado: " + file.getName());
        } else {
            System.out.println("No se seleccionó ningún archivo de audio.");
        }
    }

    /**
     * Inicializa los elementos de la vista, configurando las opciones de formato, estado y
     * las películas disponibles para seleccionar en el ComboBox.
     *
     * @param url            La URL utilizada para resolver rutas relativas.
     * @param resourceBundle Los recursos que se utilizan para localizar los elementos de la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formatocopia.getItems().setAll("DVD", "Blu-ray", "Digital", "VHS");
        estadocopia.getItems().setAll("Disponible", "Alquilada");
        peliculaDAO = new PeliculaDAO();
        List<Pelicula> peliculas = peliculaDAO.findAll();
        peliculasComboBox.getItems().addAll(peliculas);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        cantidadcopia.setValueFactory(valueFactory);
    }

    /**
     * Muestra una alerta con el mensaje especificado.
     *
     * @param title     El título de la alerta.
     * @param message   El mensaje a mostrar en la alerta.
     * @param alertType El tipo de alerta.
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
