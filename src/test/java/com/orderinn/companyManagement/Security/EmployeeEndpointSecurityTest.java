package com.orderinn.companyManagement.Security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmployeeEndpointSecurityTest {

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
    @WithMockUser(username = "system_manager", roles = {"SYSTEM_MANAGER"})
    public void systemManagerShouldAccess__getAllEmployees() throws Exception{
        mockMvc.perform(get("/api/employee/find/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void managerShouldAccess__getAllEmployees() throws Exception{
        mockMvc.perform(get("/api/employee/find/all"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "employee", roles = {"EMPLOYEE"})
    public void employeeMustNotAccessAnyEmployeeEndpoint() throws Exception{
        mockMvc.perform(get("/api/employee/find/all"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/employee/find/username/{username}", "testUser"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/employee/find/id/{id}", 12345L))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/employee/find/firstname/{firstName}","Test"))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/employee/new"))
                .andExpect(status().isForbidden());

        mockMvc.perform(put("/api/employee/transfer/{managerId}/{companyId}", 12345L, 111111L))
                .andExpect(status().isForbidden());
    }






}
