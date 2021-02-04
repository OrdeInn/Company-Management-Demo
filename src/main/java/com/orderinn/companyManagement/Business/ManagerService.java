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
public class ManagerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getManagerByUsername(String username){

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

    public List<User> getManagerByFirstName(String firstName){
        return userRepository.findByFirstName(firstName).stream().filter(user -> user.getRoleId() == 2).collect(Collectors.toList());
    }

    public List<User> getAllManagers(){
        return userRepository.findByRoleId(2);
    }

    public User saveNewManager(User user){

        if (user.getUsername() == null ||
                user.getPassword() == null ||
                user.getFirstName() == null ||
                user.getLastName() == null ||
                user.getCompanyId() == null) {

            throw new IllegalArgumentException("Fields of manager cannot be null");
        }

        if(user.getRoleId() != 2){
            throw new IllegalArgumentException("Illegal attempt to save new manager");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
