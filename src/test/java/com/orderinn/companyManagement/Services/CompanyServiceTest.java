package com.orderinn.companyManagement.Services;

import com.orderinn.companyManagement.Business.CompanyService;
import com.orderinn.companyManagement.Dal.CompanyRepository;
import com.orderinn.companyManagement.Model.Company;
import com.orderinn.companyManagement.Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static com.orderinn.companyManagement.CustomAsserts.UserAssert.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void shouldFindCompanyWithGivenId(){
        Company company = new Company(12345L, "Delta");
        Optional<Company> optionalCompany = Optional.of(company);
        given(companyRepository.findByCompanyId(12345L)).willReturn(optionalCompany);

        Company returnedCompany = companyService.getCompanyById(12345L);
        assertThat(returnedCompany.getCompanyId()).isEqualTo(12345L);
        assertThat(returnedCompany.getCompanyName()).isEqualTo("Delta");
    }

    @Test
    void throwsExceptionWhenGetCompanyWithIdIFNull(){
        assertThrows(IllegalArgumentException.class, ()->{
            companyService.getCompanyById(null);
        });
    }

    @Test
    void throwsExceptionWhenGetCompanyByIdIfNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
           companyService.getCompanyById(123L);
        });
    }

    @Test
    void throwExceptionWhenGetCompanyWithIdIfNotExist(){
        Optional<Company> optionalCompany = Optional.empty();
        given(companyRepository.findByCompanyId(any(Long.class))).willReturn(optionalCompany);

        assertThrows(IllegalArgumentException.class, ()->{
           companyService.getCompanyById(12345L);
        });
    }

    @Test
    void shouldReturnEmployeesOfCompanyGivenById(){
        Company company = new Company(111111L, "Delta");
        User employee1 = new User(12345L, "testUser", "25805026", "Test", "User", 3, "IT", 111111L);
        User employee2 = new User(12346L, "loremipsum", "password", "Lorem", "Ipsum", 3, "IT", 111111L);
        User manager = new User(12310L, "managerUser", "password", "Manager", "Ipsum", 2, "IT", 111111L);

        List<User> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(manager);

        company.setEmployees(employees);

        Optional<Company> optionalCompany = Optional.of(company);
        given(companyRepository.findByCompanyId(111111L)).willReturn(optionalCompany);

        List<User> employeesOfDelta = companyService.getEmployees(111111L);
        for(User employee : employeesOfDelta){
            customAssert(employee).hasNoNullValue();
            assertThat(employee.getCompanyId()).isEqualTo(111111L);
        }
    }

    @Test
    void throwsExceptionWhenGetEmployeesOfCompanyIfIdNotValid(){
        assertThrows(IllegalArgumentException.class, ()->{
            companyService.getEmployees(123L);
        });
    }

    @Test
    void throwsExceptionWhenGetEmployeesOfCompanyIfIdNull(){
        assertThrows(IllegalArgumentException.class,()->{
           companyService.getEmployees(null);
        });
    }

    @Test
    void shouldSaveCompanyProperly(){
        Company company = new Company(12345L, "Delta");
        given(companyRepository.save(company)).willReturn(company);

        Company returnedCompany = companyService.saveCompany(company);
        assertThat(returnedCompany.getCompanyName()).isEqualTo("Delta");
        assertThat(returnedCompany.getCompanyId()).isEqualTo(12345L);
    }

    @Test
    void throwsExceptionWhenSaveNewCompanyIfIdNotValid(){
        Company company = new Company(123L, "Delta");
        Company company1 = new Company(null, "Delta");

        assertThrows(IllegalArgumentException.class, ()->{
            companyService.saveCompany(company);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            companyService.saveCompany(company1);
        });
    }

    @Test
    void throwsExceptionWhenSaveNewCompanyIfNameNotValid(){
        Company company = new Company(12345L, null);
        Company company1 = new Company(12345L, "");

        assertThrows(IllegalArgumentException.class, ()->{
           companyService.saveCompany(company);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            companyService.saveCompany(company1);
        });
    }
}
