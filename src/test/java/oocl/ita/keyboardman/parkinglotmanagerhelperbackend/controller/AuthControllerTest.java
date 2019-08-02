package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginTokenDto;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.LoginUser;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ClerkService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

  @MockBean
  private UserService userService;

  @MockBean
  private ClerkService clerkService;

  @Autowired
  MockMvc mvc;

  @Test
  void should_return_new_user_when_register() throws Exception {

    LoginUser loginUser = new LoginUser();
    loginUser.setRole("user");
    ObjectMapper objectMapper = new ObjectMapper();
    LoginTokenDto loginTokenDto = new LoginTokenDto("222","user");
    String str = objectMapper.writeValueAsString(loginUser);

    when(userService.login(any())).thenReturn(loginTokenDto);

    mvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON).content(str))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token", is("222")))
        .andExpect(jsonPath("$.role", is("user")));
  }

  @Test
  void should_return_new_clerk_when_register() throws Exception {

    LoginUser loginUser = new LoginUser();
    loginUser.setRole("clerk");
    ObjectMapper objectMapper = new ObjectMapper();
    LoginTokenDto loginTokenDto = new LoginTokenDto("222","clerk");
    String str = objectMapper.writeValueAsString(loginUser);

    when(clerkService.login(any())).thenReturn(loginTokenDto);

    mvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON).content(str))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token", is("222")))
        .andExpect(jsonPath("$.role", is("clerk")));
  }


}