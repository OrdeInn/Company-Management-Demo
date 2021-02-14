package com.orderinn.companyManagement.Model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
public class User {

    @Id
    @SequenceGenerator(name="user_gen", sequenceName = "user_pk_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name="enabled")
    private int enabled;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "department")
    private String department;

    @Column(name = "company_id")
    private Long companyId;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false, nullable = false )
    private Company company;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", insertable = false, updatable = false,nullable = false)
    private Role role;


    public User(Long id, String username, String password, String first_name, String last_name, Integer roleId, String department, Long companyId) {
        userId = id;
        this.username = username;
        this.password = password;
        this.firstName = first_name;
        this.lastName = last_name;
        this.roleId = roleId;
        this.department = department;
        this.companyId = companyId;
    }

    public User(String username, String password, String first_name, String last_name, Integer roleId, String department, Long companyId) {
        this.username = username;
        this.password = password;
        this.firstName = first_name;
        this.lastName = last_name;
        this.roleId = roleId;
        this.department = department;
        this.companyId = companyId;
    }
}

