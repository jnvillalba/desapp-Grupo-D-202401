package ar.edu.unq.desapp.grupoD.backenddesappapi.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Role;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
//@Transactional
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private UserRepository userPersistence;
//
//    @Autowired
//    public CustomUserDetailsService(UserRepository userPersistence) {
//        this.userPersistence = userPersistence;
//    }
//
//    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roleList){
//        return roleList.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userPersistence.findById(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapToAuthorities(user.getRoles()));
//    }
//
//}