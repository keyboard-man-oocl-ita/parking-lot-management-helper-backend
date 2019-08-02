package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginTokenDto;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginUser;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.User;

public interface UserService {
  User register(User newUser) ;

  LoginTokenDto login(LoginUser user) ;

  User findByPhoneNumber(String username);

  User getUser();

  User updateUser(User user);

}
