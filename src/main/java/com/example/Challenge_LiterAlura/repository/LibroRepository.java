package com.example.Challenge_LiterAlura.repository;

import com.example.Challenge_LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface LibroRepository extends JpaRepository<Libro, Long> {
    /*Query("SELECT c FROM Libro c WHERE c.titulo = ?1")
       public List<Libro> findByTitulo (String titulo);
       */
    List<Libro> findByTitulo (String titulo);
//
//    @Query("SELECT l.id, l.idioma., l.numeroDescargas, l.titulo, l.autor.nombre FROM Libro l")
//    List<Object[]> findLibrosWithIdioma();


}
