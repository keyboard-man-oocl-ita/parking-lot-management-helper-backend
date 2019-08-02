package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ClerkDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ClerkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClerkController.class)
@ExtendWith(SpringExtension.class)
class ClerkControllerTest {
  @MockBean
  private ClerkService clerkService;

  @Autowired
  MockMvc mvc;

  @Test
  void should_find_clerks_by_manage_id() throws Exception {
    List<ClerkDTO> clerkDTOS = new ArrayList<>();
    Clerk clerk = expectClerk();
    ClerkDTO dto = new ClerkDTO(clerk);
    clerkDTOS.add(dto);

    when(clerkService.findClerkByManagedBy()).thenReturn(clerkDTOS);

    mvc.perform(get("/managers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("Liu")))
        .andExpect(jsonPath("$[0].phoneNumber", is("123123123")));
  }

  private Clerk expectClerk() {
    Clerk clerk = new Clerk();
    clerk.setName("Liu");
    clerk.setPhoneNumber("123123123");
    return clerk;
  }

  @Test
  void should_find_clerks_by_clerk_name_and_manage_id() throws Exception {
    List<ClerkDTO> clerkDTOS = new ArrayList<>();
    Clerk clerk = expectClerk();
    ClerkDTO dto = new ClerkDTO(clerk);
    clerkDTOS.add(dto);

    when(clerkService.findClerksByClerkName(any())).thenReturn(clerkDTOS);

    mvc.perform(get("/managers").param("name","Liu"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("Liu")))
        .andExpect(jsonPath("$[0].phoneNumber", is("123123123")));
  }

  @Test
  void should_find_clerk_by_clerk_phone_number_and_manage_id() throws Exception {
    Clerk clerk = expectClerk();
    ClerkDTO dto = new ClerkDTO(clerk);

    when(clerkService.findClerkByClerkPhoneNumber(any())).thenReturn(dto);

    mvc.perform(get("/managers").param("phoneNumber","123123123"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Liu")))
        .andExpect(jsonPath("$.phoneNumber", is("123123123")));
  }

  @Test
  void should_return_new_user_when_register() throws Exception {
    Clerk clerk = new Clerk();
    clerk.setPhoneNumber("123");
    clerk.setPassword("123");
    clerk.setName("Bob");
    ObjectMapper objectMapper = new ObjectMapper();
    String str = objectMapper.writeValueAsString(clerk);

    when(clerkService.register(any())).thenReturn(clerk);

    mvc.perform(post("/clerks").contentType(MediaType.APPLICATION_JSON).content(str))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.phoneNumber", is("123")))
        .andExpect(jsonPath("$.password", is("123")));
  }
  @Test
  void should_return_clerks_given_condition() throws Exception {
    Clerk clerk = new Clerk();
    clerk.setPhoneNumber("123");
    clerk.setPassword("123");
    clerk.setName("Bob");
    clerk.setRole(1);
    ObjectMapper objectMapper = new ObjectMapper();
    Clerk clerk1 = new Clerk();
    clerk1.setPhoneNumber("456");
    clerk1.setPassword("123");
    clerk1.setName("Bob");
    clerk1.setRole(1);
    List<ClerkDTO> clerkDTOS = new ArrayList<>();
    clerkDTOS.add(new ClerkDTO(clerk));
    clerkDTOS.add(new ClerkDTO(clerk1));


    when(clerkService.findClerkByNameAndRole(anyString(),anyInt())).thenReturn(clerkDTOS);

    mvc.perform(get("/admin/clerks").param("name","Bob").param("role","1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].phoneNumber", is("123")))
        .andExpect(jsonPath("$[0].role", is("clerk")));
  }
}