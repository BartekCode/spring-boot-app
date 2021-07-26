package com.bartek.restApi.security;

import com.bartek.restApi.logic.UserService;
import com.bartek.restApi.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

//public class CustomUserDetailService implements UserDetailsService {
//    private UserService userService;
//
//    public CustomUserDetailService(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userService.findByEmail(email);
//        if (user == null){
//            throw  new UsernameNotFoundException("User not found");
//        }
//        org.springframework.security.core.userdetails.User userDetails =
//                new org.springframework.security.core.userdetails.User(
//                        user.getEmail(),
//                        user.getPassword(),
//                        authority(Role.USER));
//                        return userDetails;
//    }
//
//
//
//    private Set<GrantedAuthority> authority(Role role){
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
//        authorities.add(new SimpleGrantedAuthority(Role.USER.toString()));
//        return authorities;
//    }
//
//}
