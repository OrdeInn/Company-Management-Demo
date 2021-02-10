package com.orderinn.companyManagement.Dao;


import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Model.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void shouldFindCompanyById(){
        Company company = new Company(111111L, "Delta");
        testEntityManager.persist(company);
        testEntityManager.flush();

        Company foundCompany = companyRepository.findByCompanyId(111111L).get();

        assertThat(foundCompany.getCompanyId()).isEqualTo(company.getCompanyId());
        assertThat(foundCompany.getCompanyName()).isEqualTo(company.getCompanyName());
    }

    @Test
    public void shouldGetAllCompanies(){
        Company company1 = new Company(111111L, "Delta");
        Company company2 = new Company(111112L, "Google");
        Company company3 = new Company(111113L, "Apple");

        List<Company> companies = new ArrayList<>();
        companies.add(company1);
        companies.add(company2);
        companies.add(company3);

        testEntityManager.persist(companies);
        testEntityManager.flush();

        List<Company> foundCompanies = companyRepository.findAll();

        for(Company company : foundCompanies){
            assertThat(company.getCompanyId()).isNotNull();
            assertThat(company.getCompanyName()).isNotNull();
        }
    }




}
