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
        Company company = new Company("Delta");
        testEntityManager.persistAndFlush(company);

        Company foundCompany = companyRepository.findByCompanyId(testEntityManager.getId(company, Long.class)).get();

        assertThat(foundCompany.getCompanyId()).isEqualTo(company.getCompanyId());
        assertThat(foundCompany.getCompanyName()).isEqualTo(company.getCompanyName());
    }

    @Test
    public void shouldGetAllCompanies(){
        Company company1 = new Company("Delta");
        Company company2 = new Company("Google");
        Company company3 = new Company("Apple");

        testEntityManager.persistAndFlush(company1);
        testEntityManager.persistAndFlush(company2);
        testEntityManager.persistAndFlush(company3);

        List<Company> foundCompanies = companyRepository.findAll();

        for(Company company : foundCompanies){
            assertThat(company.getCompanyId()).isNotNull();
            assertThat(company.getCompanyName()).isNotNull();
        }
    }

    @Test
    public void shouldSaveCompanyDatabaseAndReturnItWithId(){
        Company company = new Company("Delta Smart Tech.");

        Company savedCompany = companyRepository.save(company);

        assertThat(savedCompany.getCompanyId()).isNotNull();
        assertThat(savedCompany.getCompanyName()).isEqualTo(company.getCompanyName());
    }

    @Test
    public void shouldSaveCompanyAndGetItById(){
        Company company = new Company("Delta Smart Tech.");

        Company savedCompany = companyRepository.save(company);

        Company testCompany = companyRepository.findByCompanyId(savedCompany.getCompanyId()).get();

        assertThat(testCompany.getCompanyId()).isNotNull();
        assertThat(testCompany.getCompanyName()).isEqualTo(company.getCompanyName());
    }

}
