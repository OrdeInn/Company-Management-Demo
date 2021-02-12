package com.orderinn.companyManagement.Security;

import com.orderinn.companyManagement.Api.ManagerController;
import com.orderinn.companyManagement.Business.ManagerService;
import com.orderinn.companyManagement.Dal.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ManagerEndpointSecurityTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders
                                    .webAppContextSetup(context)
                                    .apply(springSecurity())
                                    .build();
    }

    @Test
    @WithMockUser(username = "system_manager", roles = {"SYSTEM_MANAGER"})
    public void systemManagerShouldAccessManagerEndpoint() throws Exception{
        mockMvc.perform(get("/api/manager/find/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "employee", roles = {"EMPLOYEE"})
    public void employeeMustNotAccessManagerEndpoint() throws Exception{
        mockMvc.perform(get("/api/manager/find/all"))
                .andExpect(status().isForbidden());
    }

}
