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
import org.example.retoconjuntohibernatejavafx.utils.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.CopiaDAO;
import org.example.retoconjuntohibernatejavafx.models.Copia;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;
import org.example.retoconjuntohibernatejavafx.models.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de películas, mostrando una lista de copias.
 * Permite visualizar, añadir, y gestionar las copias de películas del usuario actual.
 */
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

    /**
     * Carga todas las copias de películas asociadas al usuario actual y las muestra en la tabla.
     */
    private void cargarCopiasUsuario() {
        List<Copia> copias = new CopiaDAO(HibernateUtils.getSessionFactory()).findByUser(CurrentSession.currentUser.getId());
        table.getItems().clear();
        table.getItems().addAll(copias);
    }

    /**
     * Cierra la aplicación cuando se hace clic en el botón de salida.
     *
     * @param actionEvent Evento de acción del botón de salida.
     */
    @javafx.fxml.FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Cierra la sesión actual y carga la vista de inicio de sesión.
     *
     * @param actionEvent Evento de acción para el botón de cierre de sesión.
     * @throws IOException Si ocurre un error al cargar la vista de inicio de sesión.
     */
    @javafx.fxml.FXML
    public void closesession(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadFXML("view/hello-view.fxml", "Login");
    }

    /**
     * Abre la vista para añadir una nueva copia cuando se hace clic en el botón de agregar.
     *
     * @param actionEvent Evento de acción para el botón de agregar copia.
     */
    public void addcopy(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/newcopy.fxml", "Nueva Copia");
    }

    /**
     * Inicializa la vista de películas, configurando las columnas de la tabla
     * y cargando las copias de películas del usuario actual.
     *
     * @param url            La URL utilizada para resolver rutas relativas.
     * @param resourceBundle Los recursos que se utilizan para localizar los elementos de la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPeliculaTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPelicula().getTitulo()));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        cargarCopiasUsuario();

        // Configura el doble clic para abrir los detalles de la copia seleccionada
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Abrir en doble clic
                CurrentSession.currentCopia = table.getSelectionModel().getSelectedItem();
                HelloApplication.loadFXML("view/detaills-view.fxml", "Detalles de la copia");
            }
        });
    }
}
