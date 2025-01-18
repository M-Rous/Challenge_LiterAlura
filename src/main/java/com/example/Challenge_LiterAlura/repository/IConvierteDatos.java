package com.example.Challenge_LiterAlura.repository;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
