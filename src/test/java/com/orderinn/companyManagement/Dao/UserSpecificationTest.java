package com.orderinn.companyManagement.Dao;

import com.orderinn.companyManagement.Dal.UserSpecification;
import com.orderinn.companyManagement.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import static com.orderinn.companyManagement.CustomAsserts.UserAssert.*;
import java.util.List;

@SpringBootTest
public class UserSpecificationTest {

    @Autowired
    private UserSpecification userSpecification;

    @Test
    public void shouldFindManagersHasSimilarUsername(){
        List<User> managers = userSpecification.getManagersUsernamesLike("test");

        assertThat(managers.size()).isEqualTo(2);

        for(User manager : managers){
            customAssert(manager).hasNoNullValue();
            assertThat(manager.getRoleId()).isEqualTo(2);
        }
    }

    @Test
    public void shouldFindManagersHasSimilarFirstname(){
        List<User> managers = userSpecification.getManagersFirstNamesLike("Test");

        assertThat(managers.size()).isEqualTo(2);
        for(User manager : managers){
            customAssert(manager).hasNoNullValue();
            assertThat(manager.getRoleId()).isEqualTo(2);
        }
    }

    @Test
    public void shouldFindManagersHasSimilarLastname(){
        List<User> managers = userSpecification.getManagersLastNamesLike("Test");

        assertThat(managers.size()).isEqualTo(2);
        for(User manager : managers){
            customAssert(manager).hasNoNullValue();
            assertThat(manager.getRoleId()).isEqualTo(2);
        }
    }

    @Test
    public void shouldFindEmployeesHasSimilarUsername(){
        List<User> employees = userSpecification.getEmployeesUsernamesLike("test");

        assertThat(employees.size()).isEqualTo(2);
        for(User employee : employees){
            customAssert(employee).hasNoNullValue();
            assertThat(employee.getRoleId()).isEqualTo(3);
        }
    }

        @Test
        public void shouldFindEmployeesHasSimilarFirstName(){
            List<User> employees = userSpecification.getEmployeesFirstNamesLike("Test");

            assertThat(employees.size()).isEqualTo(2);
        for(User employee : employees){
            customAssert(employee).hasNoNullValue();
            assertThat(employee.getRoleId()).isEqualTo(3);
        }
    }

    @Test
    public void shouldFindEmployeesHasSimilarLastName(){
        List<User> employees = userSpecification.getEmployeesLastNamesLike("Test");

        assertThat(employees.size()).isEqualTo(2);
        for(User employee : employees){
            customAssert(employee).hasNoNullValue();
            assertThat(employee.getRoleId()).isEqualTo(3);
        }
    }

    @Test
    public void shouldGetEmployeesWorkInSameCompanyAndDepartment(){
        Long companyId = 100L;
        String department = "IT";

        List<User> employees = userSpecification.getEmployeesInSameCompanyAndDepartment(companyId, department);
        assertThat(employees.size()).isEqualTo(2);
        for(User employee : employees){
            customAssert(employee).hasNoNullValue();
            assertThat(employee.getRoleId()).isEqualTo(3);
            assertThat(employee.getDepartment()).isEqualTo(department);
            assertThat(employee.getCompanyId()).isEqualTo(companyId);
        }
    }














}
