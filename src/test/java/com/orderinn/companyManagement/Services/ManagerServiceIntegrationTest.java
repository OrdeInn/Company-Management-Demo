package com.orderinn.companyManagement.Services;

import com.orderinn.companyManagement.Business.ManagerService;
import com.orderinn.companyManagement.Dal.UserRepository;
import com.orderinn.companyManagement.Model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ManagerServiceIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ManagerService managerService;

    @Test
    public void shouldReturnManagerWithUsername(){
        User user = new User(12345L, "testUser", "25805026", "Test", "User", 2, "IT", 111111L);
        testEntityManager.persist(user);

        User manager = userRepository.findByUsername("testUser").get();

        assertThat(manager.getUserId()).isEqualTo(12345L);
        assertThat(manager.getUsername()).isEqualTo("testUser");
        assertThat(manager.getPassword()).isEqualTo("25805026");
        assertThat(manager.getFirstName()).isEqualTo("Test");
        assertThat(manager.getLastName()).isEqualTo("User");
        assertThat(manager.getRoleId()).isEqualTo(2);
        assertThat(manager.getDepartment()).isEqualTo("IT");
        assertThat(manager.getCompanyId()).isEqualTo(111111);
    }




}
