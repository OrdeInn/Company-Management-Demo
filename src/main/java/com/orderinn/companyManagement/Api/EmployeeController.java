package com.orderinn.companyManagement.Api;

import com.orderinn.companyManagement.Business.EmployeeService;
import com.orderinn.companyManagement.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService ) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<User>> getAllEmployees(){
        List<User> users =  employeeService.getAllEmployees();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<User> getEmployeeById(@PathVariable("id") Long Id){
        User user =  employeeService.getEmployeeById(Id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<User> addNewManager(@RequestBody User user){
        User returnedUser = employeeService.saveNewEmployee(user);
        return new ResponseEntity<>(returnedUser, HttpStatus.OK);
    }








}
