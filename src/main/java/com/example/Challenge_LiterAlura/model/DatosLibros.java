package com.example.Challenge_LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
    @JsonAlias("title") String titulo,
    //Genera el Record DatosAutor
    @JsonAlias("authors")
    List<DatosAutor> autor,
    @JsonAlias("languages")  List<String> idioma,
    @JsonAlias("download_count") Double numeroDeDescarga

) {
        @Override
        public String toString() {
            return "DatosLibros{" +
                    "titulo='" + titulo + '\'' +
                    ", autor=" + autor +
                    ", idiomas=" + idioma +
                    ", numeroDeDescarga=" + numeroDeDescarga +
                    '}';
        }
}
