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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

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
            HelloApplication.loadFXML("view/hello-view.fxml","Login");
        }else info.setText("Las contraseñas no coinciden");

    }

    @FXML
    public void close(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/hello-view.fxml","Login");
    }
}
