package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;
import org.example.retoconjuntohibernatejavafx.utils.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.PeliculaDAO;
import org.example.retoconjuntohibernatejavafx.models.Pelicula;
import org.example.retoconjuntohibernatejavafx.models.Usuario;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de administración de películas.
 * Permite cargar, visualizar, añadir y eliminar películas.
 */
public class AdminController implements Initializable {

    @javafx.fxml.FXML
    private TableColumn<Pelicula, Integer> colAnio;
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
    private TableColumn<Pelicula, Integer> colDuracion;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, Integer> colId;
    @javafx.fxml.FXML
    private TableColumn colClasificacion;
    @javafx.fxml.FXML
    private TableView<Pelicula> table;
    @javafx.fxml.FXML
    private Button buttonadd;

    private Usuario usuario;
    private Pelicula pelicula;

    /**
     * Carga todas las películas del usuario actual y las muestra en la tabla.
     */
    private void cargarPeliculasUsuario() {
        List<Pelicula> peliculas = new PeliculaDAO(HibernateUtils.getSessionFactory()).findAll();
        table.getItems().clear();
        table.getItems().addAll(peliculas);
    }

    /**
     * Cierra la aplicación cuando se hace clic en el botón de salida.
     *
     * @param actionEvent Evento de acción para el botón de salida.
     */
    @javafx.fxml.FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Cierra la sesión actual y carga la vista de inicio de sesión.
     *
     * @param actionEvent Evento de acción para el botón de cierre de sesión.
     */
    @javafx.fxml.FXML
    public void closesession(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/hello-view.fxml", "Login");
    }

    /**
     * Abre la vista para añadir una nueva película cuando se hace clic en el botón de agregar.
     *
     * @param actionEvent Evento de acción para el botón de agregar película.
     */
    @javafx.fxml.FXML
    public void addfilm(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/newfilm-view.fxml", "Nueva Pelicula");
    }

    /**
     * Inicializa los elementos de la tabla de películas y carga la lista de películas del usuario.
     *
     * @param url            La URL utilizada para resolver rutas relativas.
     * @param resourceBundle Los recursos que se utilizarán para localizar los elementos de la vista.
     */
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

        // Configura el menú contextual para la eliminación de películas
        table.setRowFactory(tv -> {
            TableRow<Pelicula> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem("Eliminar película");

            deleteItem.setOnAction(event -> {
                Pelicula pelicula = row.getItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Película");
                alert.setHeaderText("¿Estás seguro de que deseas eliminar esta película?");
                alert.setContentText("Película: " + pelicula.getTitulo());

                if (alert.showAndWait().get() == ButtonType.OK) {
                    new PeliculaDAO(HibernateUtils.getSessionFactory()).delete(pelicula);
                    cargarPeliculasUsuario();  // Actualiza la tabla después de eliminar
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

        // Configura el doble clic para abrir los detalles de la película seleccionada
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Abrir en doble clic
                CurrentSession.currentPelicula = table.getSelectionModel().getSelectedItem();
                HelloApplication.loadFXML("view/trailer-view.fxml", "Detalles de la copia");
            }
        });
    }
}

