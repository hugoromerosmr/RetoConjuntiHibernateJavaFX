module org.example.retoconjuntohibernatejavafx {
    requires javafx.fxml;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires javafx.web;
    requires javafx.media;

    opens org.example.retoconjuntohibernatejavafx to javafx.fxml;
    exports org.example.retoconjuntohibernatejavafx;

    opens org.example.retoconjuntohibernatejavafx.models to javafx.fxml, org.hibernate.orm.core;
    exports org.example.retoconjuntohibernatejavafx.models;

    exports org.example.retoconjuntohibernatejavafx.controllers;
    opens org.example.retoconjuntohibernatejavafx.controllers to javafx.fxml;
    exports org.example.retoconjuntohibernatejavafx.utils;
    opens org.example.retoconjuntohibernatejavafx.utils to javafx.fxml;
}
