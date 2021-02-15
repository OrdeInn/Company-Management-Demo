package com.orderinn.companyManagement.Dal;

import com.orderinn.companyManagement.Model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {


    Optional<User> findByUsername(String username);
    List<User> findAll();
    Optional<User> findByUserId(Long Id);
    List<User> findByFirstName(String firstName);
    List<User> findByRoleId(Integer roleId);

    @Override
    <S extends User> S save(S s);
}
