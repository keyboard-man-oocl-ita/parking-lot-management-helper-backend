package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public User findByUserId(String id);
    public User findByPhoneNumber(String id);
}
