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

    public HelloController() {}

    @FXML
    public void acceder(ActionEvent actionEvent) throws IOException {
        String nombre = fieldusername.getText();
        String password = fieldpassword.getText();
        Usuario usuario = new UsuarioDAO(HibernateUtils.getSessionFactory()).findUserLogin(nombre,password);

            CurrentSession.currentUser = usuario;
            sessionFactory = HibernateUtils.getSessionFactory();
            if (usuario.getRol()==0) {
                HelloApplication.loadFXML("view/film-view.fxml", "Vista de peliculas");
            }else if (usuario.getRol()==1) {
                HelloApplication.loadFXML("view/admin-view.fxml", "Vista de administrador");
            }
    }

    @FXML
    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void registrar(ActionEvent actionEvent) {
        HelloApplication.loadFXML("view/registrer-view.fxml", "Vista de registro");
    }
}
