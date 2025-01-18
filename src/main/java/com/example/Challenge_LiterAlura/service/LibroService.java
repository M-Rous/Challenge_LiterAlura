package com.example.Challenge_LiterAlura.service;

import com.example.Challenge_LiterAlura.model.Libro;
import com.example.Challenge_LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getLibroByTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

}
