package Dsa.repository;

import Dsa.entity.UserData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em; // 필드에 @PersistenceContext 적용

    // 생성자는 비워둡니다.
    public JpaUserRepository() {}

    // ... (기존 코드) ...

    // UserData엔티티에 데이터를 저장하는 메소드
    @Override
    public UserData save(UserData userdata) {
        em.persist(userdata);
        return userdata;
    }

    // 추후 아이디 찾는 용
    @Override
    public UserData findByEmail(String email) {
        List<UserData> results = em.createQuery("select u from UserData u where u.email = :email", UserData.class)
                .setParameter("email", email)  // email 매개변수 설정
                .getResultList();

        if (!results.isEmpty()) {
            // 결과가 있으면 첫 번째 항목을 반환
            System.out.println(results.get(0).getEmail());
            return results.get(0);
        } else {
            // 결과가 없으면 null을 반환
            return null;
        }
    }
    // 아이디 비번 검사
    @Override
    public UserData findByEmailAndPassword(String email, String password) {
        UserData result = em.createQuery("select u from UserData u where u.email = :email and u.password = :password", UserData.class)
                .setParameter("email", email)  // email 매개변수 설정
                .setParameter("password", password) // password 매개변수 설정
                .getSingleResult();
        return result;
    }
}