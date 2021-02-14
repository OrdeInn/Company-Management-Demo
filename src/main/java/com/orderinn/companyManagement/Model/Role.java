package com.orderinn.companyManagement.Model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="roles")
@NoArgsConstructor
@Getter
@Setter
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
}
