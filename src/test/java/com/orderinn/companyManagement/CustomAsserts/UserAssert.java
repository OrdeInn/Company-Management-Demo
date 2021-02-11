package com.orderinn.companyManagement.CustomAsserts;

import com.orderinn.companyManagement.Model.User;
import org.assertj.core.api.AbstractAssert;

public class UserAssert  extends AbstractAssert<UserAssert, User> {

    public UserAssert(User user) {
        super(user, UserAssert.class);
    }

    public static UserAssert customAssert(User actual) {
        return new UserAssert(actual);
    }

    public void hasNoNullValue() {
        isNotNull();
        if( actual.getUserId() == null ||
            actual.getUsername() == null ||
            actual.getPassword()  == null ||
            actual.getFirstName() == null ||
            actual.getLastName() == null ||
            actual.getRoleId() == null ||
            actual.getDepartment() == null ||
            actual.getCompanyId() == null)
        {
            failWithMessage("User shouldn't contains null area");
        }
    }

    public void compareEachValue(User other){
        isNotNull();

        if(actual.getUserId().compareTo(other.getUserId()) != 0){
            failWithMessage("mismatch in userId field");
        }
        if(actual.getUsername().compareTo(other.getUsername()) != 0){
            failWithMessage("mismatch in username field");
        }
        if(actual.getPassword().compareTo(other.getPassword()) != 0){
            failWithMessage("mismatch in password field");
        }
        if(actual.getFirstName().compareTo(other.getFirstName()) != 0){
            failWithMessage("mismatch in firstname field");
        }
        if(actual.getLastName().compareTo(other.getLastName()) != 0){
            failWithMessage("mismatch in lastname field");
        }
        if(actual.getRoleId().compareTo(other.getRoleId()) != 0){
            failWithMessage("mismatch in roleId field");
        }
        if(actual.getDepartment().compareTo(other.getDepartment()) != 0){
            failWithMessage("mismatch in department field");
        }
        if(actual.getCompanyId().compareTo(other.getCompanyId()) != 0){
            failWithMessage("mismatch in companyId field");
        }
    }
}
