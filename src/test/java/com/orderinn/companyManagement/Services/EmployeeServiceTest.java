package com.orderinn.companyManagement.Services;

import com.orderinn.companyManagement.Business.EmployeeService;
import com.orderinn.companyManagement.Dal.UserRepository;
import com.orderinn.companyManagement.Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeService employeeService;


    @Test
    void shouldFindEmployeeWithUsername(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUsername(user.getUsername())).willReturn(optionalUser);

        User returnedUser = employeeService.getEmployeeByUsername("testUser");

        assertThat(returnedUser.getUserId()).isEqualTo(12345L);
        assertThat(returnedUser.getUsername()).isEqualTo("testUser");
        assertThat(returnedUser.getPassword()).isEqualTo("25805026");
        assertThat(returnedUser.getFirstName()).isEqualTo("Test");
        assertThat(returnedUser.getLastName()).isEqualTo("User");
        assertThat(returnedUser.getRoleId()).isEqualTo(3);
        assertThat(returnedUser.getDepartment()).isEqualTo("IT");
        assertThat(returnedUser.getCompanyId()).isEqualTo(111111);
    }

    @Test
    void throwExceptionIfCannotFindEmployeeWithGivenUsername(){
        Optional<User> optionalUser = Optional.empty();
        given(userRepository.findByUsername(any(String.class))).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.getEmployeeByUsername("testUser");
        });
    }

    @Test
    void throwExceptionIfFoundUserWithUsernameIsNotEmployee(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 2, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);

        given(userRepository.findByUsername("testUser")).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.getEmployeeByUsername("testUser");
        });
    }

    @Test
    void shouldFindEmployeeWithUserId(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUserId(user.getUserId())).willReturn(optionalUser);
        User returnedUser = employeeService.getEmployeeById(12345L);

        assertThat(returnedUser.getUsername()).isEqualTo("testUser");
        assertThat(returnedUser.getPassword()).isEqualTo("25805026");
        assertThat(returnedUser.getFirstName()).isEqualTo("Test");
        assertThat(returnedUser.getLastName()).isEqualTo("User");
        assertThat(returnedUser.getRoleId()).isEqualTo(3);
        assertThat(returnedUser.getDepartment()).isEqualTo("IT");
        assertThat(returnedUser.getCompanyId()).isEqualTo(111111);
    }

    @Test
    void throwsExceptionIfCannotFoundEmployeeWithGivenId(){
        Optional<User> optionalUser =  Optional.empty();
        given(userRepository.findByUserId(any(Long.class))).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.getEmployeeById(12345L);
        });
    }

    @Test
    void throwExceptionIfFoundUserWithIdIsNotEmployee(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 2, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);

        given(userRepository.findByUserId(user.getUserId())).willReturn(optionalUser);
        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.getEmployeeById(user.getUserId());
        });
    }

    @Test
    void shouldReturnEmployeesWithGivenFirstname(){
        User user1 = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);
        User user2 = new User(12346L, "testUser", "56236351", "Test", "Lorem", 3, "IT", 111111L);
        User user3 = new User(12347L, "testUser", "32165463", "Test", "Ipsum", 3, "IT", 111111L);

        List<User> employees = new ArrayList<>();
        employees.add(user1);
        employees.add(user2);
        employees.add(user3);

        given(userRepository.findByFirstName("Test")).willReturn(employees);
        List<User> returnedEmployees = userRepository.findByFirstName("Test");

        for(User employee : returnedEmployees){
            assertThat(employee.getFirstName()).isEqualTo("Test");
            assertThat(employee.getRoleId()).isEqualTo(3);
        }
    }























}
