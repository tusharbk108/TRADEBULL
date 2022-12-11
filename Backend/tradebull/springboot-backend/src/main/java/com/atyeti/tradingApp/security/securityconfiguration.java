package com.atyeti.tradingApp.security;

import com.atyeti.tradingApp.models.UserModel;
import com.atyeti.tradingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class securityconfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public  BCryptPasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }



    @Override
    protected  void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/**").permitAll().and().formLogin().and().csrf().disable();

    }

    @Override
    public  void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/getAllclient");
        web.ignoring().antMatchers("/history");
        web.ignoring().antMatchers("/signin");
        web.ignoring().antMatchers("/login");
        web.ignoring().antMatchers("/forgot");
        web.ignoring().antMatchers("/remove-watchlist");
        web.ignoring().antMatchers("/register");
        web.ignoring().antMatchers("/companysearch");
        web.ignoring().antMatchers("/get-one");
        web.ignoring().antMatchers("/my-share");
        web.ignoring().antMatchers("/get-user");
        web.ignoring().antMatchers("/buy");
        web.ignoring().antMatchers("/watch-list");
        web.ignoring().antMatchers("/add-watchlist");
        web.ignoring().antMatchers("/sell");
        web.ignoring().antMatchers("/delete");
        web.ignoring().antMatchers("/addCompany");
        web.ignoring().antMatchers("/updateCompany/{company_id}");
        web.ignoring().antMatchers("/getCompanyById");
        web.ignoring().antMatchers("/add-fund");
        web.ignoring().antMatchers("/withdraw");
    }
}
