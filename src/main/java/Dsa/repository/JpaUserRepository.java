package Dsa.repository;

import Dsa.entity.UserData;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {

        private final EntityManager em;

        public JpaUserRepository(EntityManager em) {
            this.em = em;
        }



    // UserData엔티티에 데이터를 저장하는 메소드
    @Override
    public UserData save(UserData userdata) {
        em.persist(userdata);
        return userdata;
    }

    // 추후 아이디 찾는 용
    @Override
    public UserData findByEmail(String username) {
        UserData result = em.createQuery("select u from UserData u where u.email = :username", UserData.class).getSingleResult();
        return result;
    }

    // 아이디 비번 검사
    @Override
    public UserData findByEmailAndPassword(String email, String password) {
        UserData result = em.createQuery("select u from UserData u where u.email = :username and u.password = :password", UserData.class).getSingleResult();
        return result;
    }
}


