package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service;

import com.itmuch.lightsecurity.jwt.JwtOperator;
import com.itmuch.lightsecurity.jwt.UserOperator;
import lombok.RequiredArgsConstructor;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginTokenDto;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginUser;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.exception.*;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ClerkDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.ClerkRepository;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.Md5Utils.checkPassword;
import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.Md5Utils.encoderByMd5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClerkServiceImpl implements ClerkService {
    @Autowired
    private ClerkRepository clerkRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserOperator userOperator;

    private final JwtOperator jwtOperator;

    @Override
    public Clerk register(Clerk clerk) {
        String managerId = userOperator.getUser().getUsername();
        final String phoneNumber = clerk.getPhoneNumber();
        if (clerkRepository.findByPhoneNumber(phoneNumber) != null) {
            throw new UserHasExistedException();
        }
        String originalPassword = "123456";
        String str = encoderByMd5(originalPassword);
        clerk.setPassword(str);
        clerk.setRole(clerk.getRole());
        clerk.setStatus(1);
        return clerkRepository.save(clerk);
    }

    @Override
    public LoginTokenDto login(LoginUser loginUser) {
        Clerk clerk = clerkRepository.findByPhoneNumber(loginUser.getPhoneNumber());
        if (clerk.getStatus() == 0) {
            throw new ClerkIsFrozenException();
        }
        if (clerk == null) {
            throw new UserNotExistedException();
        }
        if (checkPassword(loginUser.getPassword(), clerk.getPassword())) {
            ClerkDTO clerkDTO = new ClerkDTO(clerk);
            com.itmuch.lightsecurity.jwt.User jwtUser = com.itmuch.lightsecurity.jwt.User.builder()
                    .id(1)
                    .username(clerk.getClerkId())
                    .roles(Arrays.asList(clerkDTO.getRole()))
                    .build();
            return new LoginTokenDto(jwtOperator.generateToken(jwtUser), loginUser.getRole());
        } else {
            throw new PasswordIsErrorException();
        }
    }

    @Override
    public List<ClerkDTO> findClerksByClerkName(String name) {
        String managerId = userOperator.getUser().getUsername();
        return formatClerks(clerkRepository.findByManagedByAndNameLike(managerId, "%" + name + "%"));
    }

    @Override
    public ClerkDTO findClerkByClerkPhoneNumber(String phoneNumber) {
        String managedBy = userOperator.getUser().getUsername();
        Clerk clerk = clerkRepository.findByManagedByAndPhoneNumberLike(managedBy, "%" + phoneNumber + "%");
        return formatClerks(Arrays.asList(clerk)).get(0);
    }

    @Override
    public ClerkDTO updateClerkStatus(String clerkId) throws Exception {
        Clerk clerk = clerkRepository.findByClerkId(clerkId);
        List<Order> orders = orderRepository.findAllByClerkId(clerkId).stream().filter(e -> e.getStatus() != 5).collect(Collectors.toList());
        if (orders.size() > 0) {
            throw new NoFeezeException();
        } else {
            int status = clerk.getStatus();
            if (status == 0) {
                clerk.setStatus(1);
            } else {
                clerk.setStatus(0);
            }
            return new ClerkDTO(clerkRepository.save(clerk));
        }

    }

    @Override
    public ClerkDTO findClerkByClerkId(String clerkId) {
        return new ClerkDTO(clerkRepository.findByClerkId(clerkId));
    }

    @Override
    public List<ClerkDTO> fingALl() {
        return formatClerks(clerkRepository.findAll());
    }

    @Override
    public ClerkDTO chomdClerk(String clerksId, Clerk clerk) {
        Clerk clerk1 = clerkRepository.findByClerkId(clerksId);
        clerk1.setRole(clerk.getRole());
        return new ClerkDTO(clerkRepository.save(clerk1));
    }

    @Override
    public ClerkDTO findClerkByPhoneNumberAndName(String phoneNumber, String name) {
        String mangerId = userOperator.getUser().getUsername();
        List<Clerk> clerks = clerkRepository.findByManagedBy(mangerId);
        if (!phoneNumber.isEmpty()){
            clerks = clerks.stream().filter(clerk -> clerk.getPhoneNumber() == phoneNumber).collect(Collectors.toList());
        }
        if (!name.isEmpty()){
            clerks = clerks.stream().filter(clerk -> clerk.getName() == name).collect(Collectors.toList());
        }
        return new ClerkDTO(clerks.get(0));
    }

    @Override
    public List<ClerkDTO> findParkingBoyWithoutManagedBy() {
        List<Clerk> clerks = clerkRepository.findAllByRole(1);
        return formatClerks(clerks.stream().filter(clerk -> clerk.getManagedBy() == null).collect(Collectors.toList()));
    }

    @Override
    public List<ClerkDTO> findAllManagers() {
        return formatClerks(clerkRepository.findAllByRole(2));
    }

    @Override
    public String batchSaveClerks(List<Clerk> clerks) {
        for(Clerk clerk : clerks){
            Clerk clerk1 = clerkRepository.findByClerkId(clerk.getClerkId());
            clerk1.setManagedBy(clerk.getManagedBy());
            clerkRepository.save(clerk1);
        }
        return "success";
    }

  @Override
  public List<ClerkDTO> filterClerksByManagedBy(String managedBy) {
    return formatClerks(clerkRepository.findAllByManagedBy(managedBy));
  }

  @Override
    public List<ClerkDTO> findClerkByNameAndRole(String name, int role) {
        if (role != 0 && !name.isEmpty()) {
            return formatClerks(clerkRepository.findAllByNameLikeAndRole("%" + name + "%", role));

        } else if (role == 0 && !name.isEmpty()) {
            return formatClerks(clerkRepository.findAllByNameLike("%" + name + "%"));
        } else if (role != 0 && name.isEmpty()) {
            return formatClerks(clerkRepository.findAllByRole(role));
        } else return formatClerks(clerkRepository.findAll());
    }

    @Override
    public List<ClerkDTO> findClerkByManagedBy() {
        String managedBy = userOperator.getUser().getUsername();
        return formatClerks(clerkRepository.findByManagedBy(managedBy));
    }

    public List<ClerkDTO> formatClerks(List<Clerk> clerks) {
        List<ClerkDTO> clerkDTOS = new ArrayList<>();
        for (Clerk clerk : clerks) {
            clerkDTOS.add(new ClerkDTO(clerk));
        }
        return clerkDTOS;
    }
}
