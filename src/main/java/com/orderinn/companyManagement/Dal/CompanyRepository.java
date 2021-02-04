package com.orderinn.companyManagement.Dal;

import com.orderinn.companyManagement.Model.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    List<Company> findAll();
    Optional<Company> findByCompanyId(Long Id);
    Company save(Company company);

}
