package com.orderinn.companyManagement.Dao;

import com.orderinn.companyManagement.Dal.UserRepository;
import com.orderinn.companyManagement.Model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static com.orderinn.companyManagement.CustomAsserts.UserAssert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindUserWithUsername(){
        User testUser = new User(123456L, "testManager1", "$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy",
                "TestManager1", "Test1", 2, "IT", 100L);

        Optional<User> optionalUser = userRepository.findByUsername(testUser.getUsername());
        assertThat(optionalUser.isPresent()).isTrue();

        customAssert(optionalUser.get()).hasNoNullValue();
        customAssert(optionalUser.get()).compareEachValue(testUser);
    }

    @Test
    public void shouldFindUserWithUserId(){
        User testUser = new User(123457L, "testManager2", "$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy",
                "TestManager2", "Test2", 2, "HR", 100L);

        Optional<User> optionalUser = userRepository.findByUserId(testUser.getUserId());
        assertThat(optionalUser.isPresent()).isTrue();

        customAssert(optionalUser.get()).hasNoNullValue();
        customAssert(optionalUser.get()).compareEachValue(testUser);

    }

    @Test
    public void shouldGetAllUsers(){
        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers.size()).isEqualTo(6);

        for(User user : allUsers){
            customAssert(user).hasNoNullValue();
        }
    }

    @Test
    public void shouldGetListOfUsersByFirstName(){
        User testUser = new User(123460L, "differentEmployee", "$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy",
                "DiffEmployee", "Different", 3, "HR", 100L);

        List<User> userList = userRepository.findByFirstName(testUser.getFirstName());
        assertThat(userList.size()).isEqualTo(1);

        customAssert(userList.get(0)).hasNoNullValue();
        customAssert(userList.get(0)).compareEachValue(testUser);
    }

    @Test
    public void shouldGetListOfSameRoleTypeUsers(){
        List<User> managers = userRepository.findByRoleId(2);
        assertThat(managers.size()).isEqualTo(2);

        for(User manager : managers){
            customAssert(manager).hasNoNullValue();
            assertThat(manager.getRoleId()).isEqualTo(2);
        }
    }

    @Test
    public void shouldSaveUserProperly(){
        User testUser = new User("saveTest", "password",
                "FirstName", "Lastname", 2, "IT", 100L);

        User returnedUser = userRepository.save(testUser);

        customAssert(returnedUser).hasNoNullValue();
        assertThat(returnedUser.getUserId()).isEqualTo(100000);//It is the first value of user_pk_seq sequence which is auto implemented by db.
    }










}
