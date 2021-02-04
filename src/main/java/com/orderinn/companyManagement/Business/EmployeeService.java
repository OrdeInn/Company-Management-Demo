package com.orderinn.companyManagement.Business;

import com.orderinn.companyManagement.Dal.UserRepository;
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


    @Autowired
    public EmployeeService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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


    public List<User> getManagerByFirstName(String firstName){

        List<User> managers =
                userRepository.findByFirstName(firstName).stream().filter(user -> user.getRoleId() == 3).collect(Collectors.toList());
        return managers;
    }

    public List<User> getAllEmployees(){
        return userRepository.findByRoleId(3);
    }

    public User saveNewEmployee(User user){

        if (user.getUsername() == null ||
            user.getPassword() == null ||
            user.getFirstName() == null ||
            user.getLastName() == null ||
            user.getDepartment() == null ||
            user.getCompanyId() == null)    {

            throw new IllegalArgumentException("Fields of employee cannot be null");
        }

        if(user.getRoleId() != 3){
            throw new IllegalArgumentException("Illegal attempt to save new Employee");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }



}
