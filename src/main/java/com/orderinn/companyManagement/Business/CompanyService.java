package com.orderinn.companyManagement.Business;

import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.User;
import lombok.RequiredArgsConstructor;
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
        if (Id == null || String.valueOf(Id).length() < 5){
            throw new IllegalArgumentException("Given company id is not valid.");
        }

        Optional<Company> optionalCompany = companyRepository.findByCompanyId(Id);

        if(optionalCompany.isPresent()){
            return optionalCompany.get();
        }else{
            throw new IllegalArgumentException(String.format("There is no company with id %d", Id));
        }
    }

    public List<User> getEmployees(Long Id){
        if (Id == null || String.valueOf(Id).length() < 5){
            throw new IllegalArgumentException("Given company id is not valid.");
        }

        Company company = getCompanyById(Id);
        return company.getEmployees();
    }

    public Company saveCompany(Company company){

        if(company.getCompanyId() == null || String.valueOf(company.getCompanyId()).length() < 5){
            throw new IllegalArgumentException("A company should have proper id");
        }else if(company.getCompanyName() == null || company.getCompanyName().equals("")){
            throw new IllegalArgumentException("A company should have proper name");
        }
        return companyRepository.save(company);
    }
}