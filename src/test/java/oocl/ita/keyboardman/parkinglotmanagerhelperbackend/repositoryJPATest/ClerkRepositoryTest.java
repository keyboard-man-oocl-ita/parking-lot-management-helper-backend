package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repositoryJPATest;


import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.ClerkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClerkRepositoryTest {
    @Autowired
    ClerkRepository clerkRepository;

    @Test
    void should_save_clerk() {
        Clerk clerk = new Clerk();
        clerk.setName("clerk1");
        clerk.setPhoneNumber("123456");
        clerk.setRole(1);
        clerk.setStatus(1);

        Clerk result = clerkRepository.save(clerk);

        assertEquals(clerk.getName(),result.getName());
    }

    @Test
    void should_update_clerk() {
        Clerk clerk = new Clerk();
        clerk.setName("clerk1");
        clerk.setPhoneNumber("123456");
        clerk.setRole(1);
        clerk.setStatus(1);

        clerkRepository.save(clerk);

        clerk.setName("clerk");
        clerk.setPhoneNumber("123");
        clerk.setRole(2);
        clerk.setEmail("123@qq.com");
        Clerk result = clerkRepository.save(clerk);

        assertEquals(clerk.getName(),result.getName());
        assertEquals(clerk.getEmail(),result.getEmail());
        assertEquals(clerk.getRole(),result.getRole());
        assertEquals(clerk.getPhoneNumber(),result.getPhoneNumber());
    }

    @Test
    void should_find_clerk_by_managedBy() {
        Clerk clerk = new Clerk();
        clerk.setName("clerk1");
        clerk.setPhoneNumber("123456");
        clerk.setRole(2);
        clerk.setStatus(1);
        Clerk manager = clerkRepository.save(clerk);

        Clerk clerk1 = new Clerk();
        clerk1.setName("clerk2");
        clerk1.setPhoneNumber("123456");
        clerk1.setRole(1);
        clerk1.setStatus(1);
        clerk1.setManagedBy(manager.getClerkId());

        Clerk clerk2 = new Clerk();
        clerk2.setName("clerk2");
        clerk2.setPhoneNumber("123456");
        clerk2.setRole(1);
        clerk2.setStatus(1);
        clerk2.setManagedBy(manager.getClerkId());
        clerkRepository.save(clerk1);
        clerkRepository.save(clerk2);

        assertEquals(2,clerkRepository.findByManagedBy(manager.getClerkId()).size());
    }

    @Test
    void should_page_clerk() {
        Clerk clerk1 = new Clerk();
        clerk1.setName("clerk1");
        clerk1.setPhoneNumber("123456");
        clerk1.setRole(2);
        clerk1.setStatus(1);

        Clerk clerk2 = new Clerk();
        clerk2.setName("clerk2");
        clerk2.setPhoneNumber("123456");
        clerk2.setRole(1);
        clerk2.setStatus(1);

        clerkRepository.save(clerk1);
        clerkRepository.save(clerk2);
        Pageable pageable = PageRequest.of(0,2, Sort.Direction.DESC,"name");

        Page<Clerk> clerks = clerkRepository.findAll(pageable);

        assertEquals(2,clerks.getContent().size());
    }
}
