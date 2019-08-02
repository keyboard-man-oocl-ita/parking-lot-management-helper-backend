package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controllerAPITest;


import com.fasterxml.jackson.databind.ObjectMapper;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller.ParkingLotController;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.OrderDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.ParkingLotDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.ParkingLot;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.OrderServiceImpl;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ParkingLotService;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ParkingLotServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
public class ParkingLotControllerAPITest {

    @MockBean
    private ParkingLotServiceImpl parkingLotService;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderServiceImpl orderService;

    @Autowired
    MockMvc mvc;

    @Test
    void should_find_parkingLot_by_parking_boy_id() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("zhuhai");
        parkingLot.setManagedBy("1");
        parkingLot.setLatitude(0);
        parkingLot.setLongtitude(0);
        parkingLot.setDescription("nice");
        parkingLot.setCapacity(10);
        parkingLot.setCreateBy("2");
        parkingLot.setParkingLotId("123");
        ParkingLotDTO parkingLotDTO = new ParkingLotDTO(parkingLot);
        List<ParkingLotDTO> parkingLotDTOs = new ArrayList<>();
        parkingLotDTOs.add(parkingLotDTO);

        when(parkingLotService.findParkingLotByParkingBoyId()).thenReturn(parkingLotDTOs);

        ResultActions resultActions = mvc.perform(get("/parkingBoys/parkingLots",parkingLot.getManagedBy()))
                .andExpect(jsonPath("$[0].name", is("zhuhai")))
                .andExpect(jsonPath("$[0].description", is("nice")))
                .andExpect(jsonPath("$[0].parkingLotId", is("123")));

        verify(parkingLotService).findParkingLotByParkingBoyId();
    }
    @Test
    void should_find_parkingLots_those_without_managedBy() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("zhuhai");
        parkingLot.setLatitude(0);
        parkingLot.setLongtitude(0);
        parkingLot.setDescription("nice");
        parkingLot.setCapacity(10);
        parkingLot.setCreateBy("2");
        parkingLot.setParkingLotId("123");
        ParkingLotDTO parkingLotDTO = new ParkingLotDTO(parkingLot);
        List<ParkingLotDTO> parkingLotDTOs = new ArrayList<>();
        parkingLotDTOs.add(parkingLotDTO);

        when(parkingLotService.findAllParkingLotWithoutMangedBy()).thenReturn(parkingLotDTOs);

        ResultActions resultActions = mvc.perform(get("/parkingLots").param("status","1"))
                .andExpect(jsonPath("$[0].name", is("zhuhai")))
                .andExpect(jsonPath("$[0].description", is("nice")))
                .andExpect(jsonPath("$[0].parkingLotId", is("123")));

    }
    @Test
    void should_return_with_managedBy() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setManagedBy("1");
        parkingLot.setName("zhuhai");
        parkingLot.setLatitude(0);
        parkingLot.setLongtitude(0);
        parkingLot.setDescription("nice");
        parkingLot.setCapacity(10);
        parkingLot.setCreateBy("2");
        parkingLot.setParkingLotId("123");
        ParkingLotDTO parkingLotDTO = new ParkingLotDTO(parkingLot);

        when(parkingLotService.updateManagedBy(anyString(),any())).thenReturn(parkingLotDTO);

        ResultActions resultActions = mvc.perform(patch("/parkingLots/{parkingLotId}",parkingLot.getParkingLotId()).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "    \"managedBy\" : \"1\"\n" +
                        "}"))
                .andExpect(jsonPath("$.name", is("zhuhai")))
                .andExpect(jsonPath("$.description", is("nice")))
                .andExpect(jsonPath("$.parkingLotId", is("123")));

    }
}
