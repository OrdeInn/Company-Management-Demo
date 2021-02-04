package com.orderinn.companyManagement.Api;


import com.orderinn.companyManagement.Business.ManagerService;
import com.orderinn.companyManagement.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('SYSTEM_MANAGER')")
    public ResponseEntity<List<User>> getAllManagers(){
        List<User> managers = managerService.getAllManagers();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasRole('SYSTEM_MANAGER')")
    public ResponseEntity<User> getManagerById(@PathVariable("id") Long Id){
        User user = managerService.getManagerById(Id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('SYSTEM_MANAGER')")
    public ResponseEntity<User> addNewManager(@RequestBody User user){
        User savedUser = managerService.saveNewManager(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

}
