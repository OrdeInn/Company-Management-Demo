package com.orderinn.companyManagement.Api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderinn.companyManagement.Business.EmployeeService;
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
@WebMvcTest
public class EmployeeControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private EmployeeService employeeService;

    @Before
    public void setup(){
        employeeService = Mockito.mock(EmployeeService.class);
        final EmployeeController employeeController = new EmployeeController(employeeService);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldGetAllEmployees()throws Exception{
        User employee1 = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        User employee2 = new User(12345L, "LoremIpsum", "password", "Lorem", "Ipsum", 3, "IT", 111111L);

        List<User> employeeList = Arrays.asList(employee1, employee2);
        given(employeeService.getAllEmployees()).willReturn(employeeList);

        MvcResult result = mockMvc .perform(get("/api/employee/find/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                                            .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(employeeList));
    }

    @Test
    public void shouldGetEmployeeByUsername()throws Exception{
        User employee = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        given(employeeService.getEmployeeByUsername(employee.getUsername())).willReturn(employee);

        MvcResult result = mockMvc .perform(get("/api/employee/find/username/{username}", employee.getUsername())
                                    .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(status().isOk())
                                    .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(employee));
    }

    @Test
    public void sendBadRequestStatusWhenGetEmployeeByUsernameIfNotValid()throws Exception{
        given(employeeService.getEmployeeByUsername(any(String.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(get("/api/employee/find/username/{username}", "test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetEmployeeById()throws Exception{
        User employee = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        given(employeeService.getEmployeeById(employee.getUserId())).willReturn(employee);

        MvcResult result = mockMvc .perform(get("/api/employee/find/id/{id}", employee.getUserId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(employee));
    }

    @Test
    public void sendBadRequestStatusWhenGetEmployeeByIdIfNotValid()throws Exception{
        given(employeeService.getEmployeeById(any(Long.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(get("/api/employee/find/id/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetEmployeesByFirstname()throws Exception{
        User employee1 = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        User employee2 = new User(12345L, "LoremIpsum", "password", "Test", "Ipsum", 3, "IT", 111111L);

        List<User> employeeList = Arrays.asList(employee1, employee2);
        given(employeeService.getEmployeesByFirstName(employee1.getFirstName())).willReturn(employeeList);

        MvcResult result = mockMvc .perform(get("/api/employee/find/firstname/{firstname}", employee1.getFirstName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(employeeList));
    }

    @Test
    public void sendBadRequestStatusWhenGetEmployeesByFirstnameIfNotValid()throws Exception{
        given(employeeService.getEmployeesByFirstName(any(String.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(get("/api/employee/find/firstname/{firstname}", "test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAddNewEmployee()throws Exception{
        User employee = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        given(employeeService.saveEmployee(employee)).willReturn(employee);

        MvcResult result = mockMvc .perform(post("/api/employee/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(employee));
    }

    @Test
    public void sendBadRequestStatusWhenAddNewEmployeeIfContainsNullValue()throws Exception{
        User employee = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);

        given(employeeService.saveEmployee(any(User.class))).willThrow(IllegalArgumentException.class);
        mockMvc .perform(post("/api/employee/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldTransferEmployeeToAnotherCompany()throws Exception{
        User employeeBefore = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        User employeeAfter = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111112L);

        given(employeeService.changeEmployeesCompany(employeeBefore.getUserId(), employeeAfter.getCompanyId())).willReturn(employeeAfter);

        MvcResult result = mockMvc .perform(put("/api/employee/transfer/{employeeId}/{companyId}", employeeBefore.getUserId(), employeeAfter.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(employeeAfter));
    }

    @Test
    public void sendBadRequestStatusWhileTransferringEmployeeIfContainsNullValues()throws Exception{
        given(employeeService.changeEmployeesCompany(any(Long.class), any(Long.class))).willThrow(IllegalArgumentException.class);

        mockMvc .perform(put("/api/employee/transfer/{employeeId}/{companyId}", 12345L, 111112L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
















}
