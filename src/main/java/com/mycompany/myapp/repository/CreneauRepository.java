package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Creneau;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Creneau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {

}
