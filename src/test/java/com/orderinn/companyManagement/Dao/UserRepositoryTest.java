package com.orderinn.companyManagement.Dao;


import com.orderinn.companyManagement.Dal.RoleRepository;
import com.orderinn.companyManagement.Dal.UserRepository;
import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.Role;
import com.orderinn.companyManagement.Model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static com.orderinn.companyManagement.CustomAsserts.UserAssert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Long companyId;

    @Before
    public void setupOtherTables(){
        Company company = new Company("Delta Smart Tech.");
        testEntityManager.persistAndFlush(company);
        companyId = testEntityManager.getId(company, Long.class);
    }

    @Test
    public void shouldFindUserWithUsername(){
        User user = new User("testUser", "password", "Test", "User", 2, "IT", companyId);
        testEntityManager.persistAndFlush(user);

        User testUser = userRepository.findByUsername(user.getUsername()).get();

        customAssert(testUser).hasNoNullValue();
        customAssert(testUser).compareEachValue(user);

        testEntityManager.clear();
    }

    @Test
    public void shouldFindUserWithUserId(){
        User user = new User("testUser", "password", "Test", "User", 2, "IT", companyId);

        testEntityManager.persistAndFlush(user);
        Long userId = testEntityManager.getId(user,Long.class);

        User testUser = userRepository.findByUserId(userId).get();

        customAssert(testUser).hasNoNullValue();
        customAssert(testUser).compareEachValue(user);
        testEntityManager.clear();

    }

    @Test
    public void shouldGetAllUsers(){
        User user1 = new User("testUser1", "password", "Test1", "User1", 2, "IT", companyId);
        User user2 = new User("testUser2", "password", "Test2", "User2", 3, "IT", companyId);
        User user3 = new User("testUser3", "password", "Test3", "User3", 1, "IT", companyId);

        testEntityManager.persistAndFlush(user1);
        testEntityManager.persistAndFlush(user2);
        testEntityManager.persistAndFlush(user3);

        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers.size()).isEqualTo(4);

        for(User user : allUsers){
            customAssert(user).hasNoNullValue();
        }
        testEntityManager.clear();

    }

    @Test
    public void shouldGetListOfUsersByFirstName(){
        User user1 = new User("testUser1", "password", "Test", "User1", 2, "IT", companyId);
        User user2 = new User("testUser2", "password", "Test", "User2", 1, "IT", companyId);

        testEntityManager.persistAndFlush(user1);
        testEntityManager.persistAndFlush(user2);

        List<User> userList = userRepository.findByFirstName(user1.getFirstName());
        assertThat(userList.size()).isEqualTo(2);

        for(User user : userList){
            customAssert(user).hasNoNullValue();
        }
        testEntityManager.clear();

    }

    @Test
    public void shouldGetListOfSameRoleTypeUsers(){
        User user1 = new User("testUser1", "password", "Test1", "User1", 2, "IT", companyId);
        User user2 = new User("testUser2", "password", "Test2", "User2", 2, "IT", companyId);

        testEntityManager.persistAndFlush(user1);
        testEntityManager.persistAndFlush(user2);

        List<User> userList = userRepository.findByRoleId(user1.getRoleId());
        assertThat(userList.size()).isEqualTo(2);

        for(User user : userList){
            customAssert(user).hasNoNullValue();
            assertThat(user.getRoleId()).isEqualTo(user1.getRoleId());
        }
        testEntityManager.clear();

    }

    @Test
    public void shouldSaveUserProperly(){
        User user = new User("testUser", "password", "Test", "User", 2, "IT", companyId);
        User testUser = userRepository.save(user);

        customAssert(testUser).hasNoNullValue();
        customAssert(testUser).compareEachValue(user);
        testEntityManager.clear();

    }










}
