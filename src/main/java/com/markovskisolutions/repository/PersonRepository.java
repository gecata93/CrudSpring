/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.markovskisolutions.repository;

import com.markovskisolutions.model.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    
    List<Person> findByFirstName(String firstName);
    List<Person> findAllByOrderByLastNameAscDateOfBirthAsc();
}
