package ar.edu.unq.desapp.grupoD.backenddesappapi.services;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {
    @Autowired
    PasswordEncoder passwordEnconder;
//    @Autowired
//    UserRepository userRepository;

    public void registerUser(User user){
        user.setPassword(
                passwordEnconder.encode(user.getPassword()));
        //userRepository.save(user);
    }
}
