package org.example.retoconjuntohibernatejavafx.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "copias", schema = "retohibernate")
public class Copia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "copia_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ColumnDefault("'disponible'")
    @Column(name = "estado", length = 20)
    private String estado;

    @ColumnDefault("1")
    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "formato", length = 50)
    private String formato;

    @Column(name = "audio", length = 50)
    private String audio;

}