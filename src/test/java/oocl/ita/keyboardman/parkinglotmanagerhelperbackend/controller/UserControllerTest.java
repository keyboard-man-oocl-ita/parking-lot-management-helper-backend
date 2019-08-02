package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginTokenDto;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginUser;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.User;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

  @MockBean
  private UserService userService;

  @Autowired
  MockMvc mvc;

  @Test
  void should_return_new_user_when_register() throws Exception {
    User user = new User();
    user.setPhoneNumber("123");
    user.setPassword("123");
    user.setCarLicense("123");
    user.setName("Bob");
    ObjectMapper objectMapper = new ObjectMapper();
    String str = objectMapper.writeValueAsString(user);

    when(userService.register(any())).thenReturn(user);

    mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(str))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.phoneNumber", is("123")))
        .andExpect(jsonPath("$.password", is("123")));
  }

  @Test
  void should_return_login_user_when_get_user() throws Exception {
    User user = new User();
    user.setPhoneNumber("123");
    user.setPassword("123");
    user.setCarLicense("123");
    user.setName("Bob");
    ObjectMapper objectMapper = new ObjectMapper();

    when(userService.getUser()).thenReturn(user);

    mvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.phoneNumber", is("123")))
        .andExpect(jsonPath("$.password", is("123")));
  }

}