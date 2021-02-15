package com.orderinn.companyManagement.Dao;


import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Model.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void shouldFindCompanyById(){
        Long companyId = 100L;
        String companyName = "DELTA";

        Optional<Company> optionalCompany = companyRepository.findByCompanyId(companyId);
        assertThat(optionalCompany.isPresent()).isTrue();
        assertThat(optionalCompany.get().getCompanyId()).isEqualTo(companyId);
        assertThat(optionalCompany.get().getCompanyName()).isEqualTo(companyName);
    }

    @Test
    public void shouldGetAllCompanies(){
        List<Company> foundCompanies = companyRepository.findAll();
        assertThat(foundCompanies.size()).isEqualTo(3);
        for(Company company : foundCompanies){
            assertThat(company.getCompanyId()).isNotNull();
            assertThat(company.getCompanyName()).isNotNull();
        }
    }

    @Test
    public void shouldSaveCompanyDatabaseAndReturnItWithId(){
        Company company = new Company("Amazon");

        Company savedCompany = companyRepository.save(company);

        assertThat(savedCompany.getCompanyId()).isNotNull();
        assertThat(savedCompany.getCompanyName()).isEqualTo(company.getCompanyName());
    }

    @Test
    public void shouldSaveCompanyAndGetItById(){
        Company company = new Company("Amazon");

        Company savedCompany = companyRepository.save(company);

        Optional<Company> optionalCompany = companyRepository.findByCompanyId(savedCompany.getCompanyId());
        assertThat(optionalCompany.isPresent()).isTrue();

        Company testCompany = optionalCompany.get();

        assertThat(testCompany.getCompanyId()).isNotNull();
        assertThat(testCompany.getCompanyId()).isEqualTo(savedCompany.getCompanyId());
        assertThat(testCompany.getCompanyName()).isEqualTo(company.getCompanyName());
    }

}
