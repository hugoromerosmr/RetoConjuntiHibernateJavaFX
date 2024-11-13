package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.Hibernate.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.PeliculaDAO;
import org.example.retoconjuntohibernatejavafx.models.Pelicula;
import org.example.retoconjuntohibernatejavafx.models.Usuario;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn <Pelicula, Integer> colAnio;
    @javafx.fxml.FXML
    private Button buttonexit;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> colPeliculaTitle;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> colDirector;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> colGenero;
    @javafx.fxml.FXML
    private Button buttonsession;
    @javafx.fxml.FXML
    private TableColumn <Pelicula, Integer> colDuracion;
    @javafx.fxml.FXML
    private TableColumn <Pelicula, Integer> colId;
    @javafx.fxml.FXML
    private TableColumn colClasificacion;
    @javafx.fxml.FXML
    private TableView <Pelicula> table;
    @javafx.fxml.FXML
    private Button buttonadd;

    private Usuario usuario;
    private Pelicula pelicula;


    private void cargarPeliculasUsuario(){
        List<Pelicula> peliculas = new PeliculaDAO(HibernateUtils.getSessionFactory()).findAll();
        table.getItems().clear();
        table.getItems().addAll(peliculas);
    }

    @javafx.fxml.FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @javafx.fxml.FXML
    public void closesession(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/hello-view.fxml", "Login");
    }

    @javafx.fxml.FXML
    public void addfilm(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/newfilm-view.fxml","Nueva Pelicula");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPeliculaTitle.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        cargarPeliculasUsuario();

        table.setRowFactory(tv -> {
            TableRow<Pelicula> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            // Crear opción "Eliminar Película" en el menú contextual
            MenuItem deleteItem = new MenuItem("Eliminar película");
            deleteItem.setOnAction(event -> {
                Pelicula pelicula = row.getItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Película");
                alert.setHeaderText("¿Estás seguro de que deseas eliminar esta película?");
                alert.setContentText("Película: " + pelicula.getTitulo());

                // Si el usuario confirma, elimina la película
                if (alert.showAndWait().get() == ButtonType.OK) {
                    new PeliculaDAO(HibernateUtils.getSessionFactory()).delete(pelicula);
                    cargarPeliculasUsuario();  // Actualizar la tabla después de eliminar
                }
            });

            contextMenu.getItems().add(deleteItem);

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });

    }
}
