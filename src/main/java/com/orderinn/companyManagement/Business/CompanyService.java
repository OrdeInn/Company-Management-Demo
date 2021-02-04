package com.orderinn.companyManagement.Business;

import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long Id){
        Optional<Company> optionalCompany = companyRepository.findByCompanyId(Id);

        if(optionalCompany.isPresent()){
            Company company = optionalCompany.get();
            return company;
        }else{
            throw new IllegalArgumentException(String.format("There is no company with id %d", Id));
        }
    }

    public List<User> getEmployees(Long Id){
        Company company = getCompanyById(Id);
        return company.getEmployees();
    }

    public Company addNewCompany(Company company){

        if(company.getCompanyId() == null){
            throw new IllegalArgumentException("A company should have proper id");
        }else if(company.getCompanyName()==null || company.getCompanyName() == ""){
            throw new IllegalArgumentException("A company should have proper name");
        }

        return companyRepository.save(company);

    }

}
