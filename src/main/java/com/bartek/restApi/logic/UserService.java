package com.bartek.restApi.logic;

import com.bartek.restApi.model.Role;
import com.bartek.restApi.model.User;
import com.bartek.restApi.model.projection.UserInfo;
import com.bartek.restApi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user){
        user.setRole(Role.USER);
        String codePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(codePass);
        userRepository.save(user);
    }


    public User findById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new NullPointerException("User with this id dont exist"));
    }

    public User findByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new NullPointerException("User with this email not found"+ email));
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email);
//    }

    public UserInfo userMap (User user){
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        return new UserInfo(firstName, lastName,email, password);
    }

    public String register(UserInfo userInfo){
        User user = mapper(userInfo);
        return user.toString();
    }

    public User mapper (UserInfo userInfo){
        return new User(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getEmail(), userInfo.getPassword(), Role.USER);

    }

    public List<User> findAll (){
       return userRepository.findAll();
    }
}
