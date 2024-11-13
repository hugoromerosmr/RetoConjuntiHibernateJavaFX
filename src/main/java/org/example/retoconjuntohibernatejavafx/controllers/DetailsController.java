package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.CopiaDAO;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    public void savecopia(ActionEvent actionEvent) {

        String nuevoFormato = formatocopia.getValue();
        int nuevaCantidad = cantidadcopia.getValue();

        CurrentSession.currentCopia.setFormato(nuevoFormato);
        CurrentSession.currentCopia.setCantidad(nuevaCantidad);

        new CopiaDAO(HibernateUtils.getSessionFactory()).update(CurrentSession.currentCopia);
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");
    }



    @FXML
    public void exitcopia(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");
    }

    @FXML
    public void deletecopia(ActionEvent actionEvent) {

        new CopiaDAO(HibernateUtils.getSessionFactory()).delete(CurrentSession.currentCopia);
        HelloApplication.loadFXML("view/film-view.fxml", "Lista Copias");

    }
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
    }
}