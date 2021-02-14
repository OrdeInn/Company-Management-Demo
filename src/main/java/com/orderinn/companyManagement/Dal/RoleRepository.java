package com.orderinn.companyManagement.Dal;

import com.orderinn.companyManagement.Model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer > {

    @Override
    <S extends Role> S save(S s);
}
