package com.orderinn.companyManagement.Dal;

import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.Company_;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import static org.springframework.data.jpa.domain.Specification.*;

import java.util.List;

@Component
public class CompanySpecification {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanySpecification(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompaniesNamesLike(String name){
        return companyRepository.findAll(where(nameLike(name)));
    }



    private Specification<Company> nameLike(String name){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Company_.COMPANY_NAME), "%"+name+"%");
    }
}
