package com.orderinn.companyManagement.Security;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest

public class CompanyEndpointSecurityTest {


    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;


    @Before
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void managerMustNotAccessAnyCompanyEndpoint() throws Exception{
        mockMvc.perform(get("/api/company/all"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/company/{id}", 12345L))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/company/employees/{id}", 12345L))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/company/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "employee", roles = {"EMPLOYEE"})
    public void employeeMustNotAccessAnyManagerEndpoint() throws Exception{
        mockMvc.perform(get("/api/company/all"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/company/{id}", 12345L))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/company/employees/{id}", 12345L))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/company/new"))
                .andExpect(status().isForbidden());
    }

}
