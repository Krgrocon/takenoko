package Dsa.service;


import Dsa.entity.UserData;
import Dsa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService {

        private final UserRepository userRepository;


        @Autowired
        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

    public UserData save(UserData userdata) {
        //validateDuplicateUser(userdata);
        System.out.println("확인용");
        return userRepository.save(userdata);
    }

    public UserData authenticate(String email, String password) {
        Optional<UserData> userdata = Optional.ofNullable(userRepository.findByEmailAndPassword(email, password));
        return userdata.orElse(null);
    }


}
