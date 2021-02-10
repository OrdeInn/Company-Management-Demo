package com.orderinn.companyManagement.Api;

import com.orderinn.companyManagement.Business.EmployeeService;
import com.orderinn.companyManagement.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/find/all")
    public ResponseEntity<List<User>> getAllEmployees(){
        List<User> users =  employeeService.getAllEmployees();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "find/username/{username}")
    public ResponseEntity<User> getEmployeeByUsername(@PathVariable("username") String username){
        try{
            User user = employeeService.getEmployeeByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/find/id/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable("id") Long Id){
        try{
            User user = employeeService.getEmployeeById(Id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/find/firstname/{firstName}")
    public ResponseEntity<List<User>> getEmployeesByFirstname(@PathVariable("firstName") String firstName){
        try{
            List<User> employees = employeeService.getEmployeesByFirstName(firstName);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<User> addNewManager(@RequestBody User user){
        User returnedUser;
        try{
            returnedUser = employeeService.saveEmployee(user);
            return new ResponseEntity<>(returnedUser, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/transfer/{employeeId}/{companyId}")
    @PreAuthorize("hasRole('SYSTEM_MANAGER')")
    public ResponseEntity<User> transferEmployeeAnotherCompany(@PathVariable("employeeId") Long employeeId,
                                                               @PathVariable("companyId") Long companyId){
        User transferredManager;
        try{
            transferredManager = employeeService.changeEmployeesCompany(employeeId, companyId);
            return new ResponseEntity<>(transferredManager, HttpStatus.OK);

        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
