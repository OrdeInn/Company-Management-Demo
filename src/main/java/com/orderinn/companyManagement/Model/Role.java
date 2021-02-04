package com.orderinn.companyManagement.Model;


import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name="name")
    private String name;

    public Role(Integer id, String name) {
        roleId = id;
        this.name = name;
    }

    public Role(){}

    public Integer getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }
}
