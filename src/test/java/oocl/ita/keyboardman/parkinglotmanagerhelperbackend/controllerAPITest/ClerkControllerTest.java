package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controllerAPITest;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller.ClerkController;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ClerkDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.ClerkRepository;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ClerkServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.ClerkStatusConfig.*;
import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.ClerkRoleConfig.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClerkController.class)
public class ClerkControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClerkServiceImpl clerkService;

    @Test
    void should_return_correspond_clerks_list_when_find_all_clekrs_by_manageBy() throws Exception {
        Clerk clerk = new Clerk();
        clerk.setClerkId("asdf-zxcv-qwer");
        clerk.setEmail("clerk@oocl.com");
        clerk.setManagedBy("immediate-maneger-id");
        clerk.setName("small clerk");
        clerk.setPassword("asdsadasdas");
        clerk.setRole(PARKINGBOY);
        clerk.setStatus(ACTIVE);
        clerk.setPhoneNumber("13866668888");
        List<ClerkDTO> clerkDTOS = new ArrayList<>();
        clerkDTOS.add(new ClerkDTO(clerk));

        when(clerkService.findClerkByManagedBy()).thenReturn(clerkDTOS);
        ResultActions resultActions = mvc.perform(get("/managers"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].role", is(clerkDTOS.get(0).getRole())))
                .andExpect(jsonPath("$[0].status", is(clerkDTOS.get(0).getStatus())))
                .andExpect(jsonPath("$[0].clerkId", is(clerkDTOS.get(0).getClerkId())))
                .andExpect(jsonPath("$[0].managedBy", is(clerkDTOS.get(0).getManagedBy())));
    }

    @Test
    void should_return_clerk_when_find_clerk_by_clerkId() throws Exception {
        Clerk clerk = new Clerk();
        clerk.setClerkId("asdf-zxcv-qwer");
        clerk.setEmail("clerk@oocl.com");
        clerk.setManagedBy("immediate-maneger-id");
        clerk.setName("small clerk");
        clerk.setPassword("asdsadasdas");
        clerk.setRole(PARKINGBOY);
        clerk.setStatus(ACTIVE);
        clerk.setPhoneNumber("13866668888");
        ClerkDTO clerkDTO = new ClerkDTO(clerk);

        when(clerkService.findClerkByClerkId(anyString())).thenReturn(clerkDTO);
        ResultActions resultActions = mvc.perform(get("/clerks").param("clerkId",clerk.getClerkId()));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(clerk.getName())));
    }
}
