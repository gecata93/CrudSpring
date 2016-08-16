package com.markovskisolutions.test.runner;

import com.markovskisolutions.test.resultset.ResultSet;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.logging.Logger;
import org.junit.Before;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class AbstractTestRunner {

    public static final Logger LOG = Logger.getLogger(AbstractTestRunner.class.getName());

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;
    
    @Before
    public void setupTest() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public ResultSet perform(RequestBuilder requestBuilder) throws Exception {
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        return new ResultSet(resultActions);
    }
}
