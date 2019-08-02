package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClerkRepository extends JpaRepository<Clerk, String> {
 
    List<Clerk> findByManagedBy(String managedBy);

    Clerk findByPhoneNumber(String phoneNumber);

    List<Clerk> findByManagedByAndNameLike(String manageId, String name);

    Clerk findByManagedByAndPhoneNumberLike(String manageId, String phoneNumber);

    Clerk findByClerkId(String clerkId);

    List<Clerk> findAllByNameLikeAndRole(String name, int role);

    List<Clerk> findAllByNameLike(String name);

    List<Clerk> findAllByRole(int role);

    List<Clerk> findAllByManagedBy(String managedBy);

}
