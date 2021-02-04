package com.orderinn.companyManagement.Dal;

import com.orderinn.companyManagement.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


    Optional<User> findByUsername(String username);
    List<User> findAll();
    Optional<User> findByUserId(Long Id);
    List<User> findByFirstName(String firstName);
    List<User> findByRoleId(Integer roleId);
    User save(User user);
}
