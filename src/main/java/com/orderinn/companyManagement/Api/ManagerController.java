package com.orderinn.companyManagement.Api;


import com.orderinn.companyManagement.Business.ManagerService;
import com.orderinn.companyManagement.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/find/all")
    public ResponseEntity<List<User>> getAllManagers(){
        List<User> managers = managerService.getAllManagers();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping(path = "/find/username/{username}")
    public ResponseEntity<User> getManagerByUsername(@PathVariable("username") String username){
        try{
            User user = managerService.getManagerByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/find/id/{id}")
    public ResponseEntity<User> getManagerById(@PathVariable("id") Long Id){
        User user;
        try {
            user = managerService.getManagerById(Id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/find/firstname/{firstName}")
    public ResponseEntity<List<User>> getManagersByFirstname(@PathVariable("firstName") String firstName){
        try{
            List<User> managers = managerService.getManagersByFirstName(firstName);
            return new ResponseEntity<>(managers, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<User> addNewManager(@RequestBody User user){
        User savedUser;
        try{
            savedUser = managerService.saveManager(user);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/transfer/{managerId}/{companyId}")
    public ResponseEntity<User> transferAnotherCompany(@PathVariable("managerId") Long managerId,
                                                       @PathVariable("companyId") Long companyId){

        User transferredManager;
        try{
            transferredManager  =  managerService.changeManagerCompany(managerId, companyId);
            return new ResponseEntity<>(transferredManager, HttpStatus.OK);

        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
