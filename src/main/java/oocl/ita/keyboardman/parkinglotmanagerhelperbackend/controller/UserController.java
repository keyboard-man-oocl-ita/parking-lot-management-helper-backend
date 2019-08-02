package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller;

import com.itmuch.lightsecurity.annotation.PreAuthorize;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.User;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/users")
  public User register(@RequestBody User newUser) {
    return userService.register(newUser);
  }

  @PreAuthorize("hasLogin()")
  @GetMapping("/users")
  public User getUser() {
    return userService.getUser();
  }

  @PreAuthorize("hasLogin()")
  @PutMapping("/users")
  public User updateUserInfo(@RequestBody User user){
    return userService.updateUser(user);
  }

}
