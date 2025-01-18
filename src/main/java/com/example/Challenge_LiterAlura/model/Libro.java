package com.example.Challenge_LiterAlura.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;


@Entity
@Data
@Table(name = "Libro")
public class Libro implements Serializable {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table-SEQ_LIBRO")
   // @SequenceGenerator(name = "table-SEQ_LIBRO", sequenceName = "SEQ_LIBRO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    //// Estrategia para autogenerar el ID
    private Long Id;
    // Título debe ser único y no nulo
    @Column(unique = true)
    private String titulo;
    //class Idioma Enum almacenado como String en la base de datos
    //@Enumerated(EnumType.STRING)
    private String idioma;
    private Double numeroDescargas;
    @ManyToOne
    @JoinColumn(name = "autorID", nullable = false) // Relación con Autor
    private Autor autor;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                ", autor=" + autor +
                '}';
    }

//private int autorID;
}
