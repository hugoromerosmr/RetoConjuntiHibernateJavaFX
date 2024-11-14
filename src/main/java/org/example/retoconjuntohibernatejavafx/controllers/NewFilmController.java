package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.utils.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.PeliculaDAO;
import org.example.retoconjuntohibernatejavafx.models.Pelicula;

import java.net.URL;
import java.util.ResourceBundle;

public class NewFilmController implements Initializable {
    @javafx.fxml.FXML
    private BorderPane border;
    @javafx.fxml.FXML
    private ScrollPane scroll1;
    @javafx.fxml.FXML
    private Button buttonsave;
    @javafx.fxml.FXML
    private ImageView imgcopia;
    @javafx.fxml.FXML
    private TextField director;
    @javafx.fxml.FXML
    private Button buttonexit;
    @javafx.fxml.FXML
    private VBox scroll;
    @javafx.fxml.FXML
    private TextField genre;
    @javafx.fxml.FXML
    private TextField title;
    @javafx.fxml.FXML
    private Spinner anio;
    @javafx.fxml.FXML
    private Spinner duracion;
    @javafx.fxml.FXML
    private ComboBox clasificacion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clasificacion.getItems().addAll("All Ages", "Kids", "Family", "Teens", "Mature", "Adults Only");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(90, 300, 1);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1950, 2024, 1);
        duracion.setValueFactory(valueFactory);
        anio.setValueFactory(valueFactory2);


    }

    @javafx.fxml.FXML
    public void exit(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/admin-view.fxml", "Lista Pelis");
    }

    @javafx.fxml.FXML
    public void savefilm(ActionEvent actionEvent) {
        Pelicula pelicula=new Pelicula();
        pelicula.setTitulo(title.getText());
        pelicula.setDirector(director.getText());
        pelicula.setGenero(genre.getText());
        pelicula.setDirector(director.getText());
        pelicula.setAnio((Integer) anio.getValue());
        pelicula.setDuracion((Integer) duracion.getValue());
        pelicula.setClasificacion(clasificacion.getValue().toString());
        new PeliculaDAO(HibernateUtils.getSessionFactory()).save(pelicula);
        HelloApplication.loadFXML("view/admin-view.fxml", "Lista Pelis");
    }
}
