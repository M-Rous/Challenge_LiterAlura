package com.example.Challenge_LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias ("birth_year") String fechaDeNacimiento,
        @JsonAlias ("death_year")String fechaDeFallecimiento)
{
    @Override
    public String toString() {
        return "DatosAutor{" +
                "nombre='" + nombre + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeFallecimiento='" + fechaDeFallecimiento + '\'' +
                '}';
    }
}
