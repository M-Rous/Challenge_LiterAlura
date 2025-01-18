package com.example.Challenge_LiterAlura.repository;

import com.example.Challenge_LiterAlura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    //@Query("SELECT i FROM idioma i WHERE LOWER(i.idioma) LIKE LOWER(CONCAT('%', :idioma, '%'))")
    List<Idioma> findByIdioma(String idioma);
    @Query("SELECT id FROM idioma i WHERE i.idioma=?1")
    Long findIdByIdioma( String nombre);


}
