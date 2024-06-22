package Dsa.repository;

import Dsa.entity.UserData;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  {

    UserData save(UserData userdata);
    UserData findByEmail(String username);
    UserData findByEmailAndPassword(String username, String password);
}
