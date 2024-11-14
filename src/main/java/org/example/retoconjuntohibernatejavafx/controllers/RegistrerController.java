package org.example.retoconjuntohibernatejavafx.controllers;

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
import org.example.retoconjuntohibernatejavafx.models.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de registro de usuarios.
 * Permite la creación de una nueva cuenta de usuario con email y contraseña.
 */
public class RegistrerController implements Initializable {
    @FXML
    private Button buttonregistrar;
    @FXML
    private Button buttonclose;
    @FXML
    private ImageView img;
    @FXML
    private ImageView gif;
    @FXML
    private PasswordField fieldpassword2;
    @FXML
    private PasswordField fieldpassword;
    @FXML
    private TextField fieldusername;
    @FXML
    private Label info;

    /**
     * Inicializa la vista de registro.
     * No se requiere configuración adicional en este caso.
     *
     * @param url            La URL utilizada para resolver rutas relativas.
     * @param resourceBundle Los recursos que se utilizan para localizar los elementos de la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // No se necesita configuración adicional en este momento
    }

    /**
     * Registra un nuevo usuario en la base de datos si las contraseñas coinciden.
     * Muestra un mensaje de error si las contraseñas no coinciden.
     *
     * @param actionEvent Evento de acción del botón de registrar.
     */
    @FXML
    public void registrar(ActionEvent actionEvent) {
        String username = fieldusername.getText();
        String password = "";
        if (fieldpassword.getText().equals(fieldpassword2.getText())) {
            password = fieldpassword.getText();
            Usuario usuario = new Usuario();
            usuario.setEmail(username);
            usuario.setContraseña(password);
            usuario.setRol(0L);
            usuario.setNombre(username.split("@")[0]);
            new UsuarioDAO(HibernateUtils.getSessionFactory()).save(usuario);
            HelloApplication.loadFXML("view/hello-view.fxml", "Login");
        } else {
            info.setText("Las contraseñas no coinciden");
        }
    }

    /**
     * Cierra la vista de registro y regresa a la vista de inicio de sesión.
     *
     * @param actionEvent Evento de acción del botón de cerrar.
     */
    @FXML
    public void close(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/hello-view.fxml", "Login");
    }
}
