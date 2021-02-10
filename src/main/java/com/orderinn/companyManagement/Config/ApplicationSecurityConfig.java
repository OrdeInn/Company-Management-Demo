package com.orderinn.companyManagement.Config;



import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .authorizeRequests()
                .antMatchers("/api/manager/**").hasRole("SYSTEM_MANAGER")
                .antMatchers("/api/employee/**").hasAnyRole("SYSTEM_MANAGER", "MANAGER")
                .antMatchers("/api/systemManager/**").hasRole("SYSTEM_MANAGER")
                .antMatchers("/api/company/**").hasRole("SYSTEM_MANAGER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .httpBasic();

        http.csrf().disable();
    }

    @Autowired
    protected void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication().passwordEncoder(passwordEncoder)
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select users.username, roles.name from users, roles where roles.role_id = users.role_id and username = ?");

    }
}




