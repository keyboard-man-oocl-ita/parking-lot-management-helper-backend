package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ClerkDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginTokenDto;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginUser;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;

import java.util.List;

public interface ClerkService {
    Clerk register(Clerk newUser);

    List<ClerkDTO> findClerkByManagedBy();

    LoginTokenDto login(LoginUser user);

    List<ClerkDTO> findClerksByClerkName(String name);

    ClerkDTO findClerkByClerkPhoneNumber(String phoneNumber);

    ClerkDTO updateClerkStatus(String clerkId) throws Exception;

    ClerkDTO findClerkByClerkId(String clerkId);

    List<ClerkDTO> findClerkByNameAndRole(String name, int role);

    List<ClerkDTO> fingALl();


    ClerkDTO chomdClerk(String clerksId, Clerk clerk);

    ClerkDTO findClerkByPhoneNumberAndName(String pahoneNumber, String name);

    List<ClerkDTO> findParkingBoyWithoutManagedBy();

    List<ClerkDTO> findAllManagers();

    String batchSaveClerks(List<Clerk> clerks);

    List<ClerkDTO> filterClerksByManagedBy(String managedBy);

}