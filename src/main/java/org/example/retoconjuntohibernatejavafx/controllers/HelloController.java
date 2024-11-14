package org.example.retoconjuntohibernatejavafx.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.retoconjuntohibernatejavafx.HelloApplication;
import org.example.retoconjuntohibernatejavafx.utils.HibernateUtils;
import org.example.retoconjuntohibernatejavafx.dao.UsuarioDAO;
import org.example.retoconjuntohibernatejavafx.models.CurrentSession;
import org.example.retoconjuntohibernatejavafx.models.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.retoconjuntohibernatejavafx.HelloApplication.sessionFactory;

/**
 * Controlador para la vista de inicio de sesión.
 * Permite a los usuarios iniciar sesión, registrarse o cerrar la aplicación.
 */
public class HelloController implements Initializable {

    @FXML
    private TextField fieldusername;
    @FXML
    private PasswordField fieldpassword;
    @FXML
    private Label info;
    @FXML
    private Button buttonregistro;
    @FXML
    private Button buttonclose;
    @FXML
    private ImageView img;
    @FXML
    private ImageView gif;
    @FXML
    private Button buttonacceder;

    /**
     * Constructor vacío para el controlador de la vista de inicio de sesión.
     */
    public HelloController() {}

    /**
     * Autentica al usuario en función de las credenciales ingresadas y
     * redirige a la vista correspondiente según el rol del usuario.
     *
     * @param actionEvent Evento de acción para el botón de acceder.
     * @throws IOException Si ocurre un error al cargar la vista de destino.
     */
    @FXML
    public void acceder(ActionEvent actionEvent) throws IOException {
        String nombre = fieldusername.getText();
        String password = fieldpassword.getText();
        Usuario usuario = new UsuarioDAO(HibernateUtils.getSessionFactory()).findUserLogin(nombre, password);

        CurrentSession.currentUser = usuario;
        sessionFactory = HibernateUtils.getSessionFactory();
        if (usuario.getRol() == 0) {
            HelloApplication.loadFXML("view/film-view.fxml", "Vista de peliculas");
        } else if (usuario.getRol() == 1) {
            HelloApplication.loadFXML("view/admin-view.fxml", "Vista de administrador");
        }
    }

    /**
     * Cierra la aplicación al hacer clic en el botón de cierre.
     *
     * @param actionEvent Evento de acción para el botón de cerrar.
     */
    @FXML
    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Inicializa los elementos de la vista de inicio de sesión.
     *
     * @param url            La URL utilizada para resolver rutas relativas.
     * @param resourceBundle Los recursos que se utilizan para localizar los elementos de la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialización adicional puede ir aquí si es necesario
    }

    /**
     * Redirige al usuario a la vista de registro para crear una nueva cuenta.
     *
     * @param actionEvent Evento de acción para el botón de registro.
     */
    @FXML
    public void registrar(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/registrer-view.fxml", "Vista de registro");
    }
}
