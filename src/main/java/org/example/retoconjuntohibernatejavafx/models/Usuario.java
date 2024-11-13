package org.example.retoconjuntohibernatejavafx.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "usuarios", schema = "retohibernate")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "`contraseña`", nullable = false)
    private String contraseña;

    @ColumnDefault("'usuario'")
    @Column(name = "rol", length = 20)
    private Long rol;

}