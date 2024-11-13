package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.Hibernate.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.CopiaDAO;
import org.example.retoconjuntohibernatejavafx.models.Copia;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;
import org.example.retoconjuntohibernatejavafx.models.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilmController implements Initializable {
    @javafx.fxml.FXML
    private Button buttonexit;
    @javafx.fxml.FXML
    private Button buttonsession;
    @javafx.fxml.FXML
    private TableView<Copia> table;

    private Usuario usuario;
    private CopiaDAO copiaDAO;

    @javafx.fxml.FXML
    private TableColumn<Copia, Integer> colId;
    @javafx.fxml.FXML
    private TableColumn<Copia, String> colEstado;
    @javafx.fxml.FXML
    private TableColumn<Copia, String> colFormato;
    @javafx.fxml.FXML
    private TableColumn<Copia, Integer> colCantidad;
    @javafx.fxml.FXML
    private TableColumn<Copia, String> colPeliculaTitle;
    @javafx.fxml.FXML
    private Button buttonadd;

    private void cargarCopiasUsuario() {
        List<Copia> copias = new CopiaDAO(HibernateUtils.getSessionFactory()).findByUser(CurrentSession.currentUser.getId());
        table.getItems().clear();
        table.getItems().addAll(copias);
    }


    @javafx.fxml.FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @javafx.fxml.FXML
    public void closesession(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadFXML("view/hello-view.fxml", "Login");
    }

    public void addcopy(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/newcopy.fxml","Nueva Copia");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPeliculaTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPelicula().getTitulo()));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        cargarCopiasUsuario();

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {// Abrir en doble clic
                CurrentSession.currentCopia = table.getSelectionModel().getSelectedItem();
                HelloApplication.loadFXML("view/detaills-view.fxml", "Detalles de la copia");
            }
        });
    }
}
