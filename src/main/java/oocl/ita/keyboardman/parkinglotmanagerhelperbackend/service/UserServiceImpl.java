package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service;

import com.itmuch.lightsecurity.jwt.JwtOperator;
import com.itmuch.lightsecurity.jwt.UserOperator;
import lombok.RequiredArgsConstructor;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginTokenDto;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginUser;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.exception.PasswordIsErrorException;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.exception.UserHasExistedException;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.exception.UserNotExistedException;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.User;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.Md5Utils.checkPassword;
import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.Md5Utils.encoderByMd5;

@Service

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  private final JwtOperator jwtOperator;
  private final UserOperator userOperator;

  @Override
  public User register(User newUser) {
    final String phoneNumber = newUser.getPhoneNumber();
    String str = encoderByMd5(newUser.getPassword());
    newUser.setPassword(str);
    if (userRepository.findByPhoneNumber(phoneNumber) != null) {
      throw new UserHasExistedException();
    }
    return userRepository.save(newUser);
  }

  @Override
  public LoginTokenDto login(LoginUser loginUser) {
    User user = userRepository.findByPhoneNumber(loginUser.getPhoneNumber());
    if (user == null) {
      throw new UserNotExistedException();
    }
    if (checkPassword(loginUser.getPassword(),user.getPassword())) {
      com.itmuch.lightsecurity.jwt.User jwtUser = com.itmuch.lightsecurity.jwt.User.builder()
          .id(1)
          .username(user.getUserId())
          .roles(Arrays.asList(loginUser.getRole()))
          .build();
      return new LoginTokenDto(jwtOperator.generateToken(jwtUser), loginUser.getRole());
    } else {
      throw new PasswordIsErrorException();
    }

  }

  @Override
  public User findByPhoneNumber(String username) {
    return userRepository.findByPhoneNumber(username);
  }

  @Override
  public User getUser() {
    String uid = userOperator.getUser().getUsername();
    return findByUserId(uid);
  }

  @Override
  public User updateUser(User user) {
    String userId = userOperator.getUser().getUsername();
    user.setUserId(userId);
    return userRepository.save(user);

  }

  private User findByUserId(String uid) {
    return userRepository.findByUserId(uid);
  }


}
