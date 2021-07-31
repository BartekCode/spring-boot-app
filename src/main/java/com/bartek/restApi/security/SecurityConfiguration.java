package com.bartek.restApi.security;

import com.bartek.restApi.logic.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    //czyli jak robi aplikacje weobwą przy użyciu springSecurity to jak najbardziej bedziemy uzywac csrf
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //cookie bedą nie mozlwie do sprawdzenia dla klienta
// cross site request forgery springSecurity tworzy token ktroy front musi uzyc do post put delete musi go przesłąć
// spowrotem by umowliżwic dany request, dodatkowe zabezpieczenie
                //oczywsice spring robi to sam za nas ale jak wszystko mozemy nadpisac po swojemu
                .csrf().disable() //my uzywamy obecnie tylko postmana do odpytek to mozemy wyłączyc
                .authorizeRequests()
                .antMatchers("/", "/register/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/users/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
//                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority(Role.ADMIN.name())
                // dodalismy autoryzacje przy metodach w controllerach przy uzyciu @PreAuthorized
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();//basic authentication
                .formLogin() //form based authentication
                    .loginPage("/login").permitAll() //adres do naszego formularza loginu
                    .defaultSuccessUrl("/discoveriesSite", true) //po poprawnym login redirect tutaj
                    .passwordParameter("password") //jezeli chdemy zmienic nazwe parametru hasla i username
                    .usernameParameter("username")
                .and()
                .rememberMe().tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                  //default na 2 tyg ale mozemy napdisac na inny czas tutaj np na 21 dni
                .key("somethingverysecured") //key do hashowania danych zapisanych w sesji czyli username i expiration time
                .rememberMeParameter("remember-me")//to samo dla remember me wspialismy default ale tak mozemy zmienic
                // nazwe tego parametru
                .and()
                .logout()
                .logoutUrl("/logout")
//              .logoutRequestMatcher(new AntPathRequestMatcher("/logut", "GET")) jezeli chcemy by z on CSRF logout bylo robione GET
                .clearAuthentication(true) //czyscimy Auth
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me") //jakie ciasteczka usuwamy po wylogowaniu
                .logoutSuccessUrl("/login"); //po wylogowaniu wchodzimy do login


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider()); //zeby uzywac klasy implementujące UserDetailServic
        // czyli wlasne ustawienia SpringSecurity
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }

//        @Override
//    public void configure(WebSecurity web) throws Exception { //wyłaczenie security by mozna było tworzyc bez autoryzacji
//        web.ignoring().antMatchers("/**");
//    }

}
