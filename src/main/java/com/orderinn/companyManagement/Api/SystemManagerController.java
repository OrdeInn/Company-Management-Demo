package com.orderinn.companyManagement.Api;

import com.orderinn.companyManagement.Dal.UserRepository;
import com.orderinn.companyManagement.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/systemManager")
public class SystemManagerController {

    private final UserRepository userRepository;

    @Autowired
    public SystemManagerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SYSTEM_MANAGER')")
    public ResponseEntity<List<User>> getAllSystemManagers(){
        List<User> users =  userRepository.findByRoleId(1);

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

}
