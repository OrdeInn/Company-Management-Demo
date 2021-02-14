package com.orderinn.companyManagement.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
@NoArgsConstructor
@Data
public class Company {

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long companyId;

    @Column(name = "name")
    private String companyName;

    @OneToMany(mappedBy = "company")
    private List<User> employees;


    public Company(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Company(String companyName){
        this.companyName = companyName;
    }
}
