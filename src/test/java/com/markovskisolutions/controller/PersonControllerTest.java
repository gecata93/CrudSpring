/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.markovskisolutions.controller;

import com.markovskisolutions.model.Person;
import com.markovskisolutions.repository.PersonRepository;
import com.markovskisolutions.test.resultset.ResultSet;
import com.markovskisolutions.test.runner.AbstractTestRunner;
import java.util.Map;
import javax.transaction.Transactional;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author User
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PersonControllerTest extends AbstractTestRunner {
    
    private static ResultSet resultSet;
    private static String request;
    private static Map response;
    private static Long personId;
    
    @Autowired
    PersonRepository personRepository;
    
    public void postPerson() throws JSONException, Exception{
        
    request =
                "{\n"
                    + "\"firstName\": \"DAMN\"\n" 
                + "}";
    
    resultSet = perform(MockMvcRequestBuilders.post("/persons").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(request))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());  
    }
    
    @Test
    public void getNameTest() throws JSONException, Exception {
        
        postPerson();
        String firstName = new JSONObject(resultSet.getContentAsString()).getString("firstName");

       Assert.assertEquals("DAMN",firstName);
    }
    
    @Test
    public void deletePersonTest() throws JSONException, Exception {
        
        postPerson();
        
        String firstName = new JSONObject(resultSet.getContentAsString()).getString("firstName");
        response = resultSet.getObjectFromResponce(Map.class);
        personId = Long.parseLong(response.get("id").toString());
                
    resultSet=   perform(MockMvcRequestBuilders.delete("/persons").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(request).param("id", personId.toString()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());  
                
                String deletedId = new JSONObject(resultSet.getContentAsString()).getString("id"); 
              
    Assert.assertEquals("DAMN",firstName);
    Assert.assertEquals("null", deletedId);
    }
    
    @Test
    public void updatePersonTest() throws JSONException, Exception {
        
    postPerson();
    Long id = new JSONObject(resultSet.getContentAsString()).getLong("id");
    
    request =
                "{"
                    + "\"firstName\": \"editedNewName\"" 
                + "}";
      
    mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "/persons/"+id.toString())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
         
        Person person = personRepository.findOne(id);
        
        Assert.assertEquals("editedNewName", person.getFirstName());
    }
    
    
    //TODO (must improve) re-write the test with exceptions and error codes
   //@Ignore
    @Test
    public void updateNonExistingPersonTest() throws Exception {
        
    String id = "123l4";
    request =
            "{\n"
                + "\"firstName\": \"DAMN\"\n" 
            + "}";

    resultSet=   perform(MockMvcRequestBuilders.put("/persons/" + id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(request))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());  

    Assert.assertEquals("1002", resultSet.getErrorCode());
    }
    
}
