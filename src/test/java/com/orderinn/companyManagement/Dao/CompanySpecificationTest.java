package com.orderinn.companyManagement.Dao;

import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Dal.CompanySpecification;
import com.orderinn.companyManagement.Model.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import java.util.List;

@SpringBootTest
public class CompanySpecificationTest {

    @Autowired
    private CompanySpecification companySpecification;

    @Test
    public void shouldFindCompaniesWithNameLike(){
        String nameLike = "oo";

        List<Company> companyList = companySpecification.getCompaniesNamesLike(nameLike);
        assertThat(companyList.size()).isEqualTo(1);
        assertThat(companyList.get(0).getCompanyName()).isEqualTo("Google");
        assertThat(companyList.get(0).getCompanyId()).isEqualTo(111112);
    }

}
