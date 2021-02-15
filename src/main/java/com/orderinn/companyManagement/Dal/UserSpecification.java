package com.orderinn.companyManagement.Dal;

import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.Company_;
import com.orderinn.companyManagement.Model.User;
import com.orderinn.companyManagement.Model.User_;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import static org.springframework.data.jpa.domain.Specification.*;

import java.util.List;

@Component
public class UserSpecification {

    private final UserRepository userRepository;

    private final int managerRoleId = 2;
    private final int employeeRoleId = 3;

    @Autowired
    public UserSpecification(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getManagersUsernamesLike(String name){
        return userRepository.findAll(where(isManager()).and(usernameLike(name)));
    }

    public List<User> getManagersFirstNamesLike(String name){
        return userRepository.findAll(where(isManager()).and(firstNameLike(name)));
    }

    public List<User> getManagersLastNamesLike(String name){
        return userRepository.findAll(where(isManager()).and(lastNameLike(name)));
    }


    public List<User> getEmployeesUsernamesLike(String name){
        return userRepository.findAll(where(isEmployee()).and(usernameLike(name)));
    }

    public List<User> getEmployeesFirstNamesLike(String name){
        return userRepository.findAll(where(isEmployee()).and(firstNameLike(name)));
    }

    public List<User> getEmployeesLastNamesLike(String name){
        return userRepository.findAll(where(isEmployee()).and(lastNameLike(name)));
    }

    public List<User> getEmployeesInSameCompanyAndDepartment(Long companyId, String department){
        return userRepository.findAll(where(isEmployee()).and(isInCompany(companyId)).and(isInDepartment(department)));
    }



    private Specification<User> usernameLike(String name){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.USERNAME), "%"+name+"%");
    }

    private Specification<User> firstNameLike(String name){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.FIRST_NAME), "%"+name+"%");
    }

    private Specification<User> lastNameLike(String name){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.LAST_NAME), "%"+name+"%");
    }

    private Specification<User> isManager(){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.ROLE_ID), managerRoleId);
    }
    private Specification<User> isEmployee(){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.ROLE_ID), employeeRoleId);
    }

    private Specification<User> isInCompany(Long companyId){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.COMPANY_ID), companyId));
    }

    private Specification<User> isInDepartment(String department){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.DEPARTMENT), department));
    }
}
