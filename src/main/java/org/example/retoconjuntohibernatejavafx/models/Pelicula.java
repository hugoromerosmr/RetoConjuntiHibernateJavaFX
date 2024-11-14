package org.example.retoconjuntohibernatejavafx.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "peliculas", schema = "retohibernate")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pelicula_id", nullable = false)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "genero", length = 100)
    private String genero;

    @Column(name = "director")
    private String director;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "clasificacion", length = 50)
    private String clasificacion;

    @Column(name = "video_id")
    private String videoId;

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", director='" + director + '\'' +
                ", anio=" + anio +
                ", duracion=" + duracion +
                ", clasificacion='" + clasificacion + '\'' +
                '}';
    }
}