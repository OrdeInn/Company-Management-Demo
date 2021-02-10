package com.orderinn.companyManagement.Api;


import com.orderinn.companyManagement.Business.CompanyService;
import com.orderinn.companyManagement.Model.Company;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest {


    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;



    @Test
    public void shouldGetAllEmployees(){
        Company company1 = new Company(111111L, "Delta");
        Company company2 = new Company(111112L, "Google");
        Company company3 = new Company(111113L, "Apple");

        List<Company> companyList = new ArrayList<>();
        companyList.add(company1);
        companyList.add(company2);
        companyList.add(company3);

        given(companyService.getAllCompanies()).willReturn(companyList);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<List<Company>> responseEntity =  companyController.getAllCompanies();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        List<Company> returnedCompanies = responseEntity.getBody();

        assertThat(returnedCompanies).isNotNull();
        assertThat(returnedCompanies.size()).isEqualTo(3);
        for(Company company : returnedCompanies){
            assertThat(company.getCompanyId()).isNotNull();
            assertThat(company.getCompanyName()).isNotNull();
        }

    }





























}
