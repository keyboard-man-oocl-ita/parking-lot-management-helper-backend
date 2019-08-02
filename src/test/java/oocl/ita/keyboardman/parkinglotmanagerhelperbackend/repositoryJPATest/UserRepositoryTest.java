package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repositoryJPATest;


import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.User;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void should_save_user() {
        User user1 = new User();
        user1.setName("u1");
        user1.setPassword("123456");
        user1.setCarLicense("l123");
        user1.setPhoneNumber("123");
        User user = userRepository.save(user1);

        assertEquals("u1",user.getName());
    }

    @Test
    void should_get_user_by_id() {
        User user1 = new User();
        user1.setName("u1");
        user1.setPassword("123456");
        user1.setCarLicense("l123");
        user1.setPhoneNumber("123");
        User user = userRepository.save(user1);

        User result = userRepository.findByUserId(user.getUserId());

        assertEquals(user1.getName(),result.getName());
    }
}
