package com.example.Challenge_LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosIdioma(
        @JsonAlias("Language") String idioma
) {
    @Override
    public String toString() {
        return "Idioma: "  + idioma ;
    }
}
