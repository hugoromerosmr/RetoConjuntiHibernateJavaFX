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

    @FXML
    public void savecopia(ActionEvent actionEvent) {
        Pelicula selectedPelicula = peliculasComboBox.getValue();
        String formato = formatocopia.getValue();
        String estado = estadocopia.getValue();
        Integer cantidad = cantidadcopia.getValue();

        if (selectedPelicula == null || formato == null || estado == null || cantidad == null) {
            // Mensaje de error si falta algún campo obligatorio
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
            copia.setAudio(selectedAudioFile.getName()); // Solo guarda el nombre del archivo
        } else {
            System.out.println("No se seleccionó un archivo de audio.");
        }

        // Guardar la copia en la base de datos
        new CopiaDAO(HibernateUtils.getSessionFactory()).save(copia);
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");
    }

    @FXML
    public void exitcopia(ActionEvent actionEvent) {
        border.getScene().getWindow().hide();
    }

    @FXML
    public void selectAudioFile() {
        // Crear y configurar el FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo de audio");

        // Establecer el directorio inicial
        File initialDirectory = new File("src/main/resources/org/example/retoconjuntohibernatejavafx/sound");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        // Filtrar para mostrar solo archivos de audio
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Audio", "*.mp3", "*.wav")
        );

        // Obtener el Stage actual para abrir el FileChooser sobre la ventana actual
        Stage stage = (Stage) buttonSelectAudio.getScene().getWindow();

        // Abrir el FileChooser y obtener el archivo seleccionado
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedAudioFile = file;
            System.out.println("Archivo de audio seleccionado: " + file.getName());
        } else {
            System.out.println("No se seleccionó ningún archivo de audio.");
        }
    }



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

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
