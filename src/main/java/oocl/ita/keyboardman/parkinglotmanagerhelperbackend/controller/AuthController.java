package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginTokenDto;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginUser;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ClerkService;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired
  private UserService userService;
  @Autowired
  private ClerkService clerkService;

  @PostMapping("auth")
  public LoginTokenDto loginReturnToken(@RequestBody LoginUser loginUser) {
    if (loginUser.getRole().equals("user")) {
      return userService.login(loginUser);
    }else {
      return clerkService.login(loginUser);
    }
  }
}
