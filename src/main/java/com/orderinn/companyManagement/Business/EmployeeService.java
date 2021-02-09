package com.orderinn.companyManagement.Business;

import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Dal.UserRepository;
import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    @Autowired
    public EmployeeService(UserRepository userRepository, PasswordEncoder passwordEncoder, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
    }

    public User getEmployeeByUsername(String username){

        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user;
        if(optionalUser.isPresent()){
            if(optionalUser.get().getRoleId() == 3){
                user = optionalUser.get();
            }else {
                throw new IllegalArgumentException(String.format("There is no employee with username %s ", username));
            }

        }else {
            throw new IllegalArgumentException(String.format("There is no employee with username %s ", username));
        }
        return user;
    }

    public User getEmployeeById(Long Id){
        if(Id == null || String.valueOf(Id).length() < 5 ){
            throw new IllegalArgumentException("Given id is not valid");
        }

        Optional<User> optionalUser = userRepository.findByUserId(Id);
        User user;
        if(optionalUser.isPresent()){
            if(optionalUser.get().getRoleId() == 3){
                user = optionalUser.get();
            }else {
                throw new IllegalArgumentException(String.format("There is no employee with id %d ", Id));
            }
        }else {
            throw new IllegalArgumentException(String.format("There is no employee with id %d", Id));
        }
        return user;
    }

    public List<User> getEmployeesByFirstName(String firstName){
        if(firstName == null || firstName.equals("")){
            throw new IllegalArgumentException(String.format("%s is not a valid first name", firstName));
        }
        return userRepository.findByFirstName(firstName).stream().filter(user -> user.getRoleId() == 3).collect(Collectors.toList());
    }

    public List<User> getAllEmployees(){
        return userRepository.findByRoleId(3);
    }

    public User saveEmployee(User user){

        if (user.getUsername() == null ||
            user.getPassword() == null ||
            user.getFirstName() == null ||
            user.getLastName() == null ||
            user.getDepartment() == null ||
            user.getCompanyId() == null ||
            user.getRoleId() == null){

            throw new IllegalArgumentException("Fields of employee cannot be null");
        }

        if(user.getRoleId() != 3){
            throw new IllegalArgumentException("Illegal attempt to save new Employee");
        }
        if(user.getUsername().length() <= 5){
            throw new IllegalArgumentException("username must be at least 6 character long");
        }
        if(user.getPassword().length() <= 7){
            throw new IllegalArgumentException("password must be  at least 8 character long");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User changeEmployeesCompany(Long employeeId, Long companyId){
        if(employeeId == null || String.valueOf(employeeId).length() < 5){
            throw new IllegalArgumentException("Employee id must be valid");
        }
        if(companyId == null || String.valueOf(companyId).length() < 5){
            throw new IllegalArgumentException("Company id must be valid");
        }

        Optional<User> optionalUser = userRepository.findByUserId(employeeId);
        Optional<Company> optionalCompany = companyRepository.findByCompanyId(companyId);
        User employee;
        if(optionalUser.isPresent()){
            employee = optionalUser.get();
            if(optionalCompany.isPresent()){
                employee.setCompanyId(companyId);
                return userRepository.save(employee);
            }else{
                throw new IllegalArgumentException("There is no company with given id");
            }
        }else{
            throw new IllegalArgumentException("There is no employee with given id");
        }
    }


}
