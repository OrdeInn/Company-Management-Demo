package com.orderinn.companyManagement.Services;

import com.orderinn.companyManagement.Business.EmployeeService;
import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Dal.UserRepository;
import com.orderinn.companyManagement.Model.Company;
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
import static com.orderinn.companyManagement.CustomAsserts.UserAssert.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CompanyRepository companyRepository;

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
    void throwExceptionWhenGetEmployeeByIdIfNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.getEmployeeById(123L);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.getEmployeeById(null);
        });
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
        List<User> returnedEmployees = employeeService.getEmployeesByFirstName("Test");

        for(User employee : returnedEmployees){
            customAssert(employee).hasNoNullValue();
            assertThat(employee.getFirstName()).isEqualTo("Test");
            assertThat(employee.getRoleId()).isEqualTo(3);
        }
    }

    @Test
    void throwExceptionWhenGettingEmployeesWithFirstNameIfNullOrEmpty(){
        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.getEmployeesByFirstName(null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.getEmployeesByFirstName("");
        });
    }

    @Test
    void shouldFindAllEmployeesProperly(){
        User user1 = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);
        User user2 = new User(12346L, "testUser", "56236351", "Test", "Lorem", 3, "IT", 111111L);
        User user3 = new User(12347L, "testUser", "32165463", "Test", "Ipsum", 3, "IT", 111111L);

        List<User> employees = new ArrayList<>();
        employees.add(user1);
        employees.add(user2);
        employees.add(user3);

        given(userRepository.findByRoleId(3)).willReturn(employees);

        List<User> returnedEmployees = employeeService.getAllEmployees();

        for(User employee : returnedEmployees){
            customAssert(employee).hasNoNullValue();
        }
    }

    @Test
    void shouldSaveEmployeeProperly(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);

        given(passwordEncoder.encode(any(String.class))).willReturn("This is an encoded string.");

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        given(userRepository.save(any(User.class))).willReturn(user);

        User returnedEmployee = employeeService.saveEmployee(user);

        customAssert(returnedEmployee).hasNoNullValue();
        customAssert(returnedEmployee).compareEachValue(user);
    }

    @Test
    void throwExceptionIfAnyFieldsOfEmployeeIsNull(){
        User user1 = new User(12345L, null, "25805026", "Test", "User", 3, "IT", 111111L);
        User user2 = new User(12345L, "testUser", null, "Test", "User", 3, "IT", 111111L);
        User user3 = new User(12345L, "testUser", "25805026", null, "User", 3, "IT", 111111L);
        User user4 = new User(12345L, "testUser", "25805026", "Test", null, 3, "IT", 111111L);
        User user5 = new User(12345L, "testUser", "25805026", "Test", "User", null, "IT", 111111L);
        User user6 = new User(12345L, "testUser", "25805026", "Test", "User", 3, null, 111111L);
        User user7 = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", null);

        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user1);
        });
        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user2);
        });
        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user3);
        });
        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user4);
        });
        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user5);
        });
        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user6);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.saveEmployee(user7);
        });
    }

    @Test
    void throwExceptionIfUserIsNotAnEmployee(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 2, "IT", 111111L);

        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user);
        });
    }

    @Test
    void throwExceptionWhenSaveNewEmployeeIfUsernameIsNotValid(){
        User user = new User(12345L, "test", "25805026", "Test", "User", 3, "IT", 111111L);

        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user);
        });
    }

    @Test
    void throwExceptionWhenSaveNewEmployeeIfPasswordIsNotValid(){
        User user = new User(12345L, "testUser", "short", "Test", "User", 3, "IT", 111111L);

        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.saveEmployee(user);
        });
    }

    @Test
    void shouldChangeEmployeesCompanyIdAndSave(){
        User user = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        User changedUser = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111112L);
        Company company = new Company(111112L, "Google");
        Optional<User> optionalUser = Optional.of(user);
        Optional<Company> optionalCompany = Optional.of(company);
        given(companyRepository.findByCompanyId(111112L)).willReturn(optionalCompany);
        given(userRepository.findByUserId(12345L)).willReturn(optionalUser);
        given(userRepository.save(any(User.class))).willReturn(changedUser);

        User returnedUser =  employeeService.changeEmployeesCompany(12345L, 111112L);

        customAssert(returnedUser).hasNoNullValue();
        customAssert(returnedUser).compareEachValue(changedUser);
    }

    @Test
    void throwsExceptionWhenChangeEmployeesCompanyIfEmployeeIdNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.changeEmployeesCompany(null, 111112L);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.changeEmployeesCompany(123L, 111112L);
        });
    }

    @Test
    void throwsExceptionWhenChangeEmployeesCompanyIfCompanyIdNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.changeEmployeesCompany(12345L, null);
        });assertThrows(IllegalArgumentException.class, ()->{
            employeeService.changeEmployeesCompany(12345L, 111L);
        });
    }

    @Test
    void throwsExceptionWhenChangeEmployeesCompanyIfThereIsNoEmployeeByGivenId(){
        Optional<User> emptyEmp = Optional.empty();
        given(userRepository.findByUserId(12345L)).willReturn(emptyEmp);
        Company company = new Company(111112L, "Google");
        Optional<Company> optionalCompany = Optional.of(company);
        given(companyRepository.findByCompanyId(111112L)).willReturn(optionalCompany);


        assertThrows(IllegalArgumentException.class, ()->{
           employeeService.changeEmployeesCompany(12345L, 111112L);
        });
    }

    @Test
    void throwsExceptionWhenChangeEmployeesCompanyIfThereIsNoCompanyByGivenId(){
        User user = new User(12345L, "testUser", "password", "Test", "User", 3, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUserId(12345L)).willReturn(optionalUser);
        Optional<Company> optionalCompany = Optional.empty();
        given(companyRepository.findByCompanyId(111112L)).willReturn(optionalCompany);

        assertThrows(IllegalArgumentException.class, ()->{
            employeeService.changeEmployeesCompany(12345L, 111112L);
        });
    }
}
