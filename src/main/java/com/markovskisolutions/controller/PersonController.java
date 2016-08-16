/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.markovskisolutions.controller;

import com.markovskisolutions.model.Person;
import com.markovskisolutions.repository.PersonRepository;
import java.lang.annotation.Documented;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
public class PersonController {
    
    @Autowired
    PersonRepository personRepository;
     
  //query that searches for the person first name.
    @RequestMapping(value = "/findBy/{firstname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public @ResponseBody List<Person> searchFirstName(@PathVariable(value="firstname") String firstName){
         
        List<Person> edit = personRepository.findByFirstName(firstName);
        if ( edit.isEmpty() == true) throw new IllegalArgumentException(firstName + " person not found");
        
        return personRepository.findByFirstName(firstName);
     }
     
    //query that sorts the database by last name and date of birth.
    @RequestMapping(value = "/orderBy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public @ResponseBody List<Person> orderByLastNameAndDateOfBirth(){
         
        return personRepository.findAllByOrderByLastNameAscDateOfBirthAsc();
     }           
       
    @RequestMapping(value = "/persons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Person> getAllFromDb(){
        
        return personRepository.findAll();
    }
    
    @RequestMapping(value = "/persons", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  @ResponseBody Person addPersons(@RequestBody Person person){
        
        return personRepository.save(person);
    }
    
    @RequestMapping(value = "/persons/{personId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Person updatePerson(@RequestBody Person person,@PathVariable(value="personId" ) Long id) throws Exception{
        
       Person edit = personRepository.findOne(id);
       edit.setFirstName(person.getFirstName());
       
       return personRepository.save(edit);
    }
    
    @RequestMapping(value = "/persons", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Person deletePerson(@RequestParam(value="id") Long id){
        
        personRepository.delete(id);
        return new Person();
    }
    
    @RequestMapping(value = "/persons/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Person findOne(@PathVariable(value = "personId") Long personId){
        
       Person edit= personRepository.findOne(personId); 
       if ( edit == null) throw new IllegalArgumentException("non existing id");
        
       return personRepository.findOne(personId);
    }

}
