package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.CopiaDAO;
import org.example.retoconjuntohibernatejavafx.dao.PeliculaDAO;
import org.example.retoconjuntohibernatejavafx.models.Copia;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;
import org.example.retoconjuntohibernatejavafx.models.Pelicula;

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

    private PeliculaDAO peliculaDAO;
    private CopiaDAO copiaDAO;

    @FXML
    public void savecopia(ActionEvent actionEvent) {
        Pelicula selectedPelicula = peliculasComboBox.getValue();
        String formato = formatocopia.getValue();
        String estado = estadocopia.getValue();
        Integer cantidad = cantidadcopia.getValue();

        Copia copia = new Copia();
        copia.setPelicula(selectedPelicula);
        copia.setUsuario(CurrentSession.currentUser);
        copia.setFormato(formato);
        copia.setEstado(estado);
        copia.setCantidad(cantidad);

        new CopiaDAO(HibernateUtils.getSessionFactory()).save(copia);
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");

    }

    @FXML
    public void exitcopia(ActionEvent actionEvent) {
        border.getScene().getWindow().hide();
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
}
