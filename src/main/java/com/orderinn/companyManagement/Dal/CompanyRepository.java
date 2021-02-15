package com.orderinn.companyManagement.Dal;

import com.orderinn.companyManagement.Model.Company;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    List<Company> findAll();
    Optional<Company> findByCompanyId(Long Id);

    @Override
    <S extends Company> S save(S s);
}
