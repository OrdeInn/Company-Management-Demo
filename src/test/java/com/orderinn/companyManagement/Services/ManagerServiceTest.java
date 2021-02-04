package com.orderinn.companyManagement.Services;


import com.orderinn.companyManagement.Business.ManagerService;
import com.orderinn.companyManagement.Dal.UserRepository;
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


@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

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
        assertThat(testUser).isEqualTo(user);
        assertThat(testUser.getRoleId()).isEqualTo(2);
        assertThat(testUser.getUserId()).isEqualTo(12345L);
        assertThat(testUser.getFirstName()).isEqualTo("Test");
        assertThat(testUser.getLastName()).isEqualTo("User");
        assertThat(testUser.getPassword()).isEqualTo("25805026");
        assertThat(testUser.getDepartment()).isEqualTo("IT");
        assertThat(testUser.getCompanyId()).isEqualTo(111111L);
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

        assertThat(managerService.getManagerById(12345L).getUserId()).isEqualTo(12345L);
        assertThat(managerService.getManagerById(12345L).getUsername()).isEqualTo("testUser");
        assertThat(managerService.getManagerById(12345L).getPassword()).isEqualTo("25805026");
        assertThat(managerService.getManagerById(12345L).getFirstName()).isEqualTo("Test");
        assertThat(managerService.getManagerById(12345L).getLastName()).isEqualTo("User");
        assertThat(managerService.getManagerById(12345L).getRoleId()).isEqualTo(2);
        assertThat(managerService.getManagerById(12345L).getRole().getName()).isEqualTo("MANAGER");
        assertThat(managerService.getManagerById(12345L).getDepartment()).isEqualTo("IT");
        assertThat(managerService.getManagerById(12345L).getCompanyId()).isEqualTo(111111);
    }

    @Test
    void throwExceptionWhenFoundUserIsNull(){
        Optional<User> optionalUser = Optional.empty();
        given(userRepository.findByUserId(any(Long.class))).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            User testUser = managerService.getManagerById(12345L);
        });
    }

    @Test
    void throwExceptionWhenFoundUserWithIdIsNotManager(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);
        Optional<User> optionalUser = Optional.of(user);

        given(userRepository.findByUserId(12345L)).willReturn(optionalUser);

        assertThrows(IllegalArgumentException.class, ()->{
            User manager = managerService.getManagerById(12345L);
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
        List<User> returnedManagers = userRepository.findByFirstName("Test");

        for(User manager : returnedManagers){
            assertThat(manager.getFirstName()).isEqualTo("Test");
            assertThat(manager.getRoleId()).isEqualTo(2);
        }
    }

    /*
    @Test
    void shouldReturnEmptyListIfThereIsNoManagerWithGivenFirstname(){
        List<User> managers = new ArrayList<>();
        given(userRepository.findByFirstName("testUSer")).willReturn(managers);

        assertThat(userRepository.findByFirstName("testUser").size()).isEqualTo(0);
    }

     */

    @Test
    void shouldReturnListOfAllManagers(){
        User user1 = new User(12345L, "testUser1", "25805026", "Test1", "User1", 2, "IT", 111111L);
        User user2 = new User(12346L, "testUser2", "12345682", "Test2", "User2", 2, "IT", 111111L);
        User user3 = new User(12347L, "testUser3", "54153516", "Test3", "User3", 2, "IT", 111111L);

        List<User> managers = new ArrayList<>();
        managers.add(user1);
        managers.add(user2);
        managers.add(user3);

        given(userRepository.findAll()).willReturn(managers);

        List<User> returnedManagers = userRepository.findAll();
        for(User manager : returnedManagers){
            assertThat(manager.getUserId()).isNotNull();
            assertThat(manager.getUsername()).isNotNull();
            assertThat(manager.getPassword()).isNotNull();
            assertThat(manager.getFirstName()).isNotNull();
            assertThat(manager.getLastName()).isNotNull();
            assertThat(manager.getRoleId()).isNotNull();
            assertThat(manager.getDepartment()).isNotNull();
            assertThat(manager.getCompanyId()).isNotNull();
        }
    }

    @Test
    void shouldSaveManagerProperly(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 2, "IT", 111111L);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        System.out.println(encodedPassword);

        given(userRepository.save(any(User.class))).willReturn(user);

        User returnedUser = userRepository.save(user);

        assertThat(returnedUser.getUserId()).isEqualTo(12345L);
        assertThat(returnedUser.getUsername()).isEqualTo("testUser");
        assertThat(returnedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(returnedUser.getFirstName()).isEqualTo("Test");
        assertThat(returnedUser.getLastName()).isEqualTo("User");
        assertThat(returnedUser.getRoleId()).isEqualTo(2);
        assertThat(returnedUser.getDepartment()).isEqualTo("IT");
        assertThat(returnedUser.getCompanyId()).isEqualTo(111111);
    }

    @Test
    void throwExceptionIfAnyFieldsOfManagerIsNull(){
        User user = new User(12345L, null, "25805026", "Test", "User", 2, "IT", 111111L);

        assertThrows(IllegalArgumentException.class , ()->{
            managerService.saveNewManager(user);
        });
    }

    @Test
    void throwExceptionIfUserIsNotManager(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 1, "IT", 111111L);

        assertThrows(IllegalArgumentException.class, ()->{
            managerService.saveNewManager(user);
        });
    }
}
