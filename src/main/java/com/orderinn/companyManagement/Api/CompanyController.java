package com.orderinn.companyManagement.Api;

import com.orderinn.companyManagement.Business.CompanyService;
import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('SYSTEM_MANAGER')")
    public ResponseEntity<List<Company>> getAllCompanies(){
        List<Company> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasRole('SYSTEM_MANAGER')")
    public ResponseEntity<Company> getCompany(@PathVariable("id") Long Id){
        Company company = companyService.getCompanyById(Id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("employees")
    @PreAuthorize("hasRole('SYSTEM_MANAGER')")
    public ResponseEntity<List<User>> getEmployees(@RequestParam("id") Long Id){
        List<User> employees = companyService.getEmployees(Id);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('SYSTEM_MANAGER')")
    public ResponseEntity<Company> addNewCompany(@RequestBody Company company){
        Company returnedCompany = companyService.addNewCompany(company);
        return new ResponseEntity<>(returnedCompany, HttpStatus.OK);
    }




}
