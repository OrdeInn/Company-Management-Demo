package com.orderinn.companyManagement.Api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderinn.companyManagement.Business.CompanyService;
import com.orderinn.companyManagement.Model.Company;
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

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CompanyController.class)
public class CompanyControllerTest {


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private CompanyService companyService;


    @Before
    public void setup(){
        companyService = Mockito.mock(CompanyService.class);
        final CompanyController companyController = new CompanyController(companyService);
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void verifyGetAllCompaniesRequestMatching() throws Exception{
        mockMvc.perform(get("/api/company/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAllCompaniesProperly() throws Exception{

        Company company1 = new Company(111111L, "Delta");
        Company company2 = new Company(111112L, "Google");
        Company company3 = new Company(111113L, "Apple");

        List<Company> companies = Arrays.asList(company1, company2, company3);

        Mockito.when(companyService.getAllCompanies()).thenReturn(companies);
        MvcResult result =  mockMvc.perform(get("/api/company/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String actualResponse  = result.getResponse().getContentAsString();

        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(companies));
    }

    @Test
    public void shouldGetCompanyByGivenId() throws Exception{
        Company company = new Company(111111L, "Delta");
        given(companyService.getCompanyById(111111L)).willReturn(company);

        mockMvc.perform(get("/api/company/{id}", 111111L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/api/company/{id}", 111111L).contentType(MediaType.APPLICATION_JSON))
                                    .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(company));
    }

    @Test
    public void sendBadRequestStatusWhenGetCompanyByIdIfIdNotValid() throws Exception{
        Company company = new Company(123L, "Delta");
        given(companyService.getCompanyById(company.getCompanyId())).willThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/company/{id}", company.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetEmployeesOfCompany() throws Exception{
        Company company = new Company(111111L, "Delta");
        User  employee1 = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        User  employee2 = new User(12346L, "loremIpsum", "password", "Lorem", "Ipsum", 2, "IT", 111111L);

        company.setEmployees(Arrays.asList(employee1, employee2));
        given(companyService.getEmployees(company.getCompanyId())).willReturn(company.getEmployees());

        MvcResult result =  mockMvc.perform(get("/api/company/employees/{id}", company.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String returnedJson = result.getResponse().getContentAsString();
        assertThat(returnedJson).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(company.getEmployees()));
    }

    @Test
    public void sendBadRequestStatusWhenGetEmployeesIfIdNotValid()throws Exception{
        Company company = new Company(123L, "Delta");
        given(companyService.getEmployees(company.getCompanyId())).willThrow(IllegalArgumentException.class);

        MvcResult result =  mockMvc.perform(get("/api/company/employees/{id}", company.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isNullOrEmpty();
    }

    @Test
    public void  shouldAddNewCompanyToSystemProperly()throws Exception{
        Company company = new Company(111111L, "Delta");

        given(companyService.saveCompany(any(Company.class))).willReturn(company);

        MvcResult result = mockMvc.perform(post("/api/company/new")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(company)))
                                    .andExpect(status().isOk())
                                    .andReturn();

        assertThat(result.getResponse().getContentAsString())
                                                        .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(company));
    }

    @Test
    public void sendBadRequestWhenAddNewCompanyToSystemIfCompanyNameNull()  throws Exception  {
        Company company = new Company(111111L, null);

        given(companyService.saveCompany(any(Company.class))).willThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/api/company/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isBadRequest());
    }
}
