package com.example.Challenge_LiterAlura.repository;

import com.example.Challenge_LiterAlura.model.Autor;
import com.example.Challenge_LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@EnableJpaRepositories
public interface AutorRepository extends JpaRepository<Autor, Long> {
    //@Query("SELECT c FROM Autor c WHERE c.nombre = ?1")
    //public List<Autor> findByNombre(String nombre);
    List<Autor> findByNombre(String nombre);

    @Query("SELECT id FROM Autor c WHERE c.nombre = ?1")
    //public long findIdByNombre(String nombre);
    Long findIdByNombre( String nombre);

//    @Query("SELECT c FROM Autor c WHERE c.fechaFallecimiento >?!")
//    List<Autor>finByFechaFallecimiento(String fechaFallecimiento);
    //las dos opciones realizan lo mismo
    @Query("SELECT c FROM Autor c WHERE c.fechaFallecimiento <= :fechaFallecimiento")
    List<Autor> findAutoresByFechaAniosVividos(@Param("fechaFallecimiento") String fechaFallecimiento);


}
