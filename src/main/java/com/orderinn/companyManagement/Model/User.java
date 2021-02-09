package com.orderinn.companyManagement.Model;


import javax.persistence.*;

@Entity
@Table(name="users")
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

    @ManyToOne
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

    public User() {
    }


    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getDepartment() {
        return department;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

