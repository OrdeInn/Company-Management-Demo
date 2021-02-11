package com.orderinn.companyManagement.Services;


import com.orderinn.companyManagement.Business.ManagerService;
import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Dal.UserRepository;
import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.Role;
import com.orderinn.companyManagement.Model.User;
import org.junit.jupiter.api.BeforeEach;
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
public class ManagerServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private ManagerService managerService;


    @BeforeEach
    @Test
    void makeSureAllComponentsNotNull(){
        assertThat(userRepository).isNotNull();
        assertThat(managerService).isNotNull();
    }

    @Test
    void shouldFindManagerWithUsername(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 2, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUsername("testUser")).willReturn(optionalUser);

        User testUser = managerService.getManagerByUsername("testUser");

        customAssert(testUser).hasNoNullValue();
        customAssert(testUser).compareEachValue(user);
    }

    @Test
    void throwExceptionWhenGetManagerByUsernameIfNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagerByUsername(null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagerByUsername("test");
        });

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagerByUsername("");
        });
    }

    @Test
    void throwExceptionWhenFoundUserWithUsernameIsNotManager(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUsername("testUser")).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagerByUsername("testUser");
        });
    }

    @Test
    void throwExceptionIfUserCannotFoundWithGivenUsername(){
        Optional<User> optionalUser = Optional.empty();
        given(userRepository.findByUsername(any(String.class))).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagerByUsername("testUser");
        });
    }

    @Test
    void shouldFindManagerById(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 2, "IT", 111111L);
        Role role = new Role(2, "MANAGER");
        user.setRole(role);
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUserId(12345L)).willReturn(optionalUser);

        User returnedManager = managerService.getManagerById(12345L);

        customAssert(returnedManager).hasNoNullValue();
        customAssert(returnedManager).compareEachValue(user);
    }

    @Test
    void throwExceptionWhenGetManagerByIdIfNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
           managerService.getManagerById(123L);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagerById(null);
        });
    }

    @Test
    void throwExceptionWhenFoundUserIsNull(){
        Optional<User> optionalUser = Optional.empty();
        given(userRepository.findByUserId(any(Long.class))).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagerById(12345L);
        });
    }

    @Test
    void throwExceptionWhenFoundUserWithIdIsNotManager(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);

        given(userRepository.findByUserId(12345L)).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagerById(12345L);
        });
    }

    @Test
    void shouldFindListOfManagersWithGivenFirstname(){
        User user1 = new User(12345L, "testUser1", "25805026", "Test", "User", 2, "IT", 111111L);
        User user2 = new User(12346L, "testUser2", "13245665", "Test", "Lorem", 2, "IT", 111111L);
        User user3 = new User(12347L, "testUser3", "68421654", "Test", "Ipsum", 2, "IT", 111111L);

        List<User> managers = new ArrayList<>();
        managers.add(user1);
        managers.add(user2);
        managers.add(user3);

        given(userRepository.findByFirstName("Test")).willReturn(managers);
        List<User> returnedManagers = managerService.getManagersByFirstName("Test");

        for(User manager : returnedManagers){
            assertThat(manager.getFirstName()).isEqualTo("Test");
            assertThat(manager.getRoleId()).isEqualTo(2);
        }
    }

    @Test
    void throwExceptionWhenGetManagersByFirstnameIfNull(){
        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagersByFirstName(null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.getManagersByFirstName("");
        });
    }

    @Test
    void shouldReturnListOfAllManagers(){
        User user1 = new User(12345L, "testUser1", "25805026", "Test1", "User1", 2, "IT", 111111L);
        User user2 = new User(12346L, "testUser2", "12345682", "Test2", "User2", 2, "IT", 111111L);
        User user3 = new User(12347L, "testUser3", "54153516", "Test3", "User3", 2, "IT", 111111L);

        List<User> managers = new ArrayList<>();
        managers.add(user1);
        managers.add(user2);
        managers.add(user3);

        given(userRepository.findByRoleId(2)).willReturn(managers);

        List<User> returnedManagers = managerService.getAllManagers();
        for(User manager : returnedManagers){
            customAssert(manager).hasNoNullValue();
        }
    }

    @Test
    void shouldSaveManagerProperly(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 2, "IT", 111111L);

        given(passwordEncoder.encode(any(String.class))).willReturn("This is an encoded string.");

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        given(userRepository.save(any(User.class))).willReturn(user);

        User returnedUser = managerService.saveManager(user);

        customAssert(returnedUser).hasNoNullValue();
        customAssert(returnedUser).compareEachValue(user);
    }

    @Test
    void throwExceptionWhenSaveManagerIfAnyFieldsOfManagerIsNull(){
        User user = new User(12345L, null, "25805026", "Test", "User", 2, "IT", 111111L);
        User user1 = new User(12345L, "testUser", null, "Test", "User", 1, "IT", 111111L);
        User user2 = new User(12345L, "testUser", "25805026", null, "User", 1, "IT", 111111L);
        User user3 = new User(12345L, "testUser", "25805026", "Test", null, 1, "IT", 111111L);
        User user4 = new User(12345L, "testUser", "25805026", "Test", "User", 1, null, 111111L);
        User user5 = new User(12345L, "testUser", "25805026", "Test", "User", 1, "IT", null);

        assertThrows(IllegalArgumentException.class , ()->{
            managerService.saveManager(user);
        });
        assertThrows(IllegalArgumentException.class , ()->{
            managerService.saveManager(user1);
        });
        assertThrows(IllegalArgumentException.class , ()->{
            managerService.saveManager(user2);
        });
        assertThrows(IllegalArgumentException.class , ()->{
            managerService.saveManager(user3);
        });
        assertThrows(IllegalArgumentException.class , ()->{
            managerService.saveManager(user4);
        });
        assertThrows(IllegalArgumentException.class , ()->{
            managerService.saveManager(user5);
        });
    }

    @Test
    void throwExceptionWhenSaveManagerIfUserIsNotManager(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 1, "IT", 111111L);

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.saveManager(user);
        });
    }

    @Test
    void throwExceptionWhenSaveManagerIfUsernameNotValid(){
        User user = new User(12345L, "test", "25805026", "Test", "User", 2, "IT", 111111L);
        assertThrows(IllegalArgumentException.class, ()->{
           managerService.saveManager(user);
        });
    }

    @Test
    void throwExceptionWhenSaveManagerIfPasswordNotValid(){
        User user = new User(12345L, "testUser", "short", "Test", "User", 2, "IT", 111111L);

        assertThrows(IllegalArgumentException.class, ()->{
           managerService.saveManager(user);
        });
    }

    @Test
    void shouldChangeManagersCompanyProperly(){
        User user = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUserId(12345L)).willReturn(optionalUser);

        Company company = new Company(111112L, "Google");
        Optional<Company> optionalCompany = Optional.of(company);
        given(companyRepository.findByCompanyId(111112L)).willReturn(optionalCompany);

        User changedUser = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111112L);
        given(userRepository.save(any(User.class))).willReturn(changedUser);

        User changedManager = managerService.changeManagerCompany(12345L, 111112L);

        customAssert(changedManager).hasNoNullValue();
        customAssert(changedManager).compareEachValue(changedUser);
    }

    @Test
    void throwsExceptionWhenChangeManagerCompanyIfManagerIdNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
            managerService.changeManagerCompany(null, 111112L);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            managerService.changeManagerCompany(123L, 111112L);
        });
    }

    @Test
    void throwsExceptionWhenChangeManagerCompanyIfCompanyIdNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
            managerService.changeManagerCompany(12345L, null);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            managerService.changeManagerCompany(12345L, 112L);
        });
    }

    @Test
    void throwsExceptionWhenChangeManagerCompanyIfThereIsNoManagerByGivenId(){
        Company company = new Company(111112L, "Google");
        Optional<Company> optionalCompany = Optional.of(company);
        given(companyRepository.findByCompanyId(111112L)).willReturn(optionalCompany);

        Optional<User> optionalUser = Optional.empty();
        given(userRepository.findByUserId(12345L)).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
           managerService.changeManagerCompany(12345L, 111112L);
        });
    }

    @Test
    void throwsExceptionWhenChangeManagerCompanyIfThereIsNoCompanyByGivenId(){
        User user = new User(12345L, "testUser", "password", "Test", "User", 2, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUserId(12345L)).willReturn(optionalUser);

        Optional<Company> optionalCompany = Optional.empty();
        given(companyRepository.findByCompanyId(111112L)).willReturn(optionalCompany);

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.changeManagerCompany(12345L, 111112L);
        });
    }























}
