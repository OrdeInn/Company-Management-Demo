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
public class ManagerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    @Autowired
    public ManagerService(UserRepository userRepository, PasswordEncoder passwordEncoder, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
    }

    public User getManagerByUsername(String username){
        if(username == null || username.length() <= 5 ){
            throw new IllegalArgumentException("invalid username");
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user;
        if(optionalUser.isPresent()){
            if(optionalUser.get().getRoleId() == 2){
                user = optionalUser.get();
            }else {
                throw new IllegalArgumentException(String.format("There is no manager with username %s ", username));
            }

        }else {
            throw new IllegalArgumentException(String.format("There is no manager with username %s ", username));
        }
        return user;
    }

    public User getManagerById(Long Id){

        if(Id == null || String.valueOf(Id).length() < 5){
            throw new IllegalArgumentException("Given id is not valid");
        }

        Optional<User> optionalUser = userRepository.findByUserId(Id);
        User user;
        if(optionalUser.isPresent()){
            if(optionalUser.get().getRoleId() == 2){
                user = optionalUser.get();
            }else {
                throw new IllegalArgumentException(String.format("There is no manager with id %d ", Id));
            }
        }else {
            throw new IllegalArgumentException(String.format("There is no manager with id %d", Id));
        }
        return user;
    }

    public List<User> getManagersByFirstName(String firstName){

        if(firstName == null || firstName.equals("")){
            throw new IllegalArgumentException(String.format("%s is not a valid first name", firstName));
        }
        return userRepository.findByFirstName(firstName).stream().filter(user -> user.getRoleId() == 2).collect(Collectors.toList());
    }

    public List<User> getAllManagers(){
        return userRepository.findByRoleId(2);
    }

    public User saveManager(User user){

        if (    user.getUsername() == null ||
                user.getPassword() == null ||
                user.getFirstName() == null ||
                user.getLastName() == null ||
                user.getCompanyId() == null ||
                user.getRoleId() == null ||
                user.getDepartment() == null) {

            throw new IllegalArgumentException("Fields of manager cannot be null");
        }

        if(user.getRoleId() != 2){
            throw new IllegalArgumentException("Illegal attempt to save new manager");
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

    public User changeManagerCompany(Long managerId, Long companyId){
        if(managerId == null || String.valueOf(managerId).length() < 5){
            throw new IllegalArgumentException("Given manager id is not valid.");
        }
        if(companyId == null || String.valueOf(companyId).length() < 5){
            throw new IllegalArgumentException("Given company id is not valid.");
        }

        Optional<User> optionalUser = userRepository.findByUserId(managerId);
        Optional<Company> optionalCompany = companyRepository.findByCompanyId(companyId);

        if(optionalUser.isPresent()){
            if(optionalCompany.isPresent()){
                User manager = optionalUser.get();
                manager.setCompanyId(companyId);
                return userRepository.save(manager);
            }else{
                throw new IllegalArgumentException("There is no company with given id.");
            }
        }else{
            throw new IllegalArgumentException("There is no manager with given id.");
        }
    }
}
