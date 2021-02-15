package com.orderinn.companyManagement.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderinn.companyManagement.Business.ManagerService;
import com.orderinn.companyManagement.Model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import java.util.Arrays;
import java.util.List;



@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ManagerController.class)
public class ManagerControllerTest {


    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ManagerService managerService;

    @Before
    public void setup(){
        managerService  = Mockito.mock(ManagerService.class);
        final ManagerController managerController = new ManagerController(managerService);
        mockMvc = MockMvcBuilders .standaloneSetup(managerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReturnAllManagers()throws Exception{
        User manager1 = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        User manager2 = new User(12345L, "LoremIpsum", "password", "Lorem", "Ipsum", 2, "IT", 111111L);
        List<User> managerList = Arrays.asList(manager1, manager2);
        given(managerService.getAllManagers()).willReturn(managerList);

        MvcResult result =  mockMvc .perform(get("/api/manager/find/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                                            .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(managerList));
    }

    @Test
    public void shouldGetManagerByUsername()throws Exception{
        User manager = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        given(managerService.getManagerByUsername(manager.getUsername())).willReturn(manager);

        MvcResult result =  mockMvc .perform(get("/api/manager/find/username/{username}", manager.getUsername())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                                            .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(manager));
    }

    @Test
    public void sendBadRequestStatusWhenGetManagerByUsernameIfNotValid()throws Exception{
        given(managerService.getManagerByUsername(any(String.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(get("/api/manager/find/username/{username}", "test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetManagerById()throws Exception{
        User manager = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        given(managerService.getManagerById(manager.getUserId())).willReturn(manager);

        MvcResult result =  mockMvc .perform(get("/api/manager/find/id/{id}", manager.getUserId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(manager));
    }

    @Test
    public void sendBadRequestStatusWhenGetManagerByIdIfNotValid()throws Exception{
        given(managerService.getManagerById(any(Long.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(get("/api/manager/find/id/{id}", 12345L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetManagersByFirstname()throws Exception{
        User manager1 = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        User manager2 = new User(12346L, "LoremIpsum", "password", "Test", "Loremipsum", 2, "IT", 111111L);
        List<User> managerList = Arrays.asList(manager1,  manager2);
        given(managerService.getManagersByFirstName(manager1.getFirstName())).willReturn(managerList);

        MvcResult result =  mockMvc .perform(get("/api/manager/find/firstname/{firstname}", manager1.getFirstName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(managerList));
    }

    @Test
    public void sendBadRequestStatusWhenGetManagerByFirstnameIfNotValid()throws Exception{
        given(managerService.getManagersByFirstName(any(String.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(get("/api/manager/find/firstname/{firstname}", "Test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAddNewManagerProperly()throws Exception{
        User manager = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        given(managerService.saveManager(any(User.class))).willReturn(manager);

        MvcResult result =  mockMvc .perform(post("/api/manager/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                                            .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(manager));
    }

    @Test
    public void sendBadRequestStatusWhenAddNewManagerIfContainsNullArea()throws Exception{
        User manager = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        given(managerService.saveManager(any(User.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(post("/api/manager/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void transferManagerToAnotherCompany()throws Exception{
        User managerBefore = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        User managerAfter = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111112L);
        given(managerService.changeManagerCompany(12345L, 111112L)).willReturn(managerAfter);

        MvcResult result =  mockMvc .perform(put("/api/manager/transfer/{managerId}/{companyId}", managerAfter.getUserId(), managerAfter.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(managerBefore)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(managerAfter));
    }

    @Test
    public void sendBadRequestStatusWhileTransferringManagerIfIdsNotValid()throws Exception{
        User manager = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        given(managerService.changeManagerCompany(any(Long.class), any(Long.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(put("/api/manager/transfer/{managerId}/{companyId}", manager.getUserId(), manager.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)))
                .andExpect(status().isBadRequest());
    }









}
