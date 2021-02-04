package com.orderinn.companyManagement.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name")
    private String companyName;

    @OneToMany(mappedBy = "company")
    private List<User> employees;


    public Company(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Company(){}


    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }
}
