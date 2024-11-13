package org.example.retoconjuntohibernatejavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;

public class HelloApplication extends Application {

    public static SessionFactory sessionFactory;
    private static Stage ventana;
    @Override
    public void start(Stage stage) throws IOException {
        ventana=stage;
        loadFXML("view/hello-view.fxml","Ventana de Login");
        stage.show();
    }

    public static void loadFXML(String view, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1200, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
        ventana.setResizable(false);
    }
        public static void main (String[]args){
            launch();
        }
    }
