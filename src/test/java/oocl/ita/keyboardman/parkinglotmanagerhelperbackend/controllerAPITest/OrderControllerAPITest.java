package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controllerAPITest;

import com.fasterxml.jackson.databind.ObjectMapper;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller.OrderController;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.OrderDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.OrderServiceImpl;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.ParkingLotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.OrderStatusConfig.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerAPITest {

     
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private ParkingLotServiceImpl parkingLotService;

    @Autowired
    MockMvc mvc;

   @Test
    public void return_clerks_order() throws Exception {
        Order order = new Order();
        order.setClerkId("1");
        order.setCreatedTime(123123123);
        order.setCarLicense("粤R 12345");
        order.setOrderId("jsadkjasldasd");
        order.setStatus(RECEIPTED);
        order.setUserId("1");
        List<OrderDTO> orders = new ArrayList<>();
        orders.add(new OrderDTO(order));

        when(orderService.findOrderByClerkIdAndStatus(anyInt())).thenReturn(orders);

        ResultActions resultActions = mvc.perform(get("/orders").param("status","1"))
                .andExpect(jsonPath("$[0].createdTime", is(123123123)))
                .andExpect(jsonPath("$[0].carLicense", is("粤R 12345")))
                .andExpect(jsonPath("$[0].status", is("已接单")))
                .andExpect(jsonPath("$[0].orderId", is("jsadkjasldasd")));


    }

    @Test
    void should_get_grab_order_when_parking_lot_not_full() throws Exception {
        Order order = new Order();
        order.setParkingLotId("1");
        order.setUserId("1");
        order.setStatus(0);
        order.setEndTime(0);
        order.setCreatedTime(0);
        order.setCarLicense("粤R 12345");
        order.setEndTime(0);
        order.setOrderId("12312445");
        OrderDTO orderDTO = new OrderDTO(order);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        orderDTOS.add(orderDTO);

        when(orderService.findGrabOrderByParkingBoyId()).thenReturn(orderDTOS);

        ResultActions resultActions = mvc.perform(get("/clerks/orders"))
                .andExpect(jsonPath("$[0].carLicense", is("粤R 12345")))
                .andExpect(jsonPath("$[0].status", is("抢单中")));

    }

    @Test
    void should_update_order() throws Exception {
        Order order = new Order();
        order.setParkingLotId("1");
        order.setUserId("1");
        order.setStatus(0);
        order.setEndTime(0);
        order.setCreatedTime(0);
        order.setCarLicense("粤R 12345");
        order.setEndTime(0);
        order.setOrderId("12312445");
        OrderDTO orderDTO = new OrderDTO(order);

        when(orderService.updateOrder(any())).thenReturn(orderDTO);

        ResultActions resultActions = mvc.perform(put("/orders",order)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order)))
                .andExpect(jsonPath("$.userId",is("1")))
                .andExpect(jsonPath("$.status",is("抢单中")))
                .andExpect(jsonPath("$.carLicense",is("粤R 12345")))
                .andExpect(jsonPath("$.orderId",is("12312445")));
    }

    @Test
    void should_create_order() throws Exception {
       Order order = new Order();
       order.setCreatedTime(new Date().getTime());
       order.setStatus(0);
       order.setCarLicense("粤C 30514");
       order.setUserId("eb4b582f-274f-442f-a415-ab9272522e16");
       OrderDTO orderDTO = new OrderDTO(order);

       when(orderService.createOrder(any())).thenReturn(orderDTO);

        ResultActions resultActions = mvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order)))
                .andExpect(status().isOk());

        verify(orderService).createOrder(any());
    }
    @Test
    void should_find_order_by_user_id() throws Exception {
        Order order = new Order();
        order.setCreatedTime(new Date().getTime());
        order.setStatus(0);
        order.setCarLicense("粤C 30514");
        order.setUserId("eb4b582f-274f-442f-a415-ab9272522e16");

        List<OrderDTO> orderDTOs = new ArrayList<>();
        orderDTOs.add(new OrderDTO(order));

        when(orderService.findOrderByUserId()).thenReturn(orderDTOs);

        ResultActions resultActions = mvc.perform(get("/users/orders"))
                .andExpect(status().isOk());
    }


    @Test
    void should_return_correct_orderDTO_when_find_filter_orders_given_manager_id() throws Exception {
        Order order = new Order();
        order.setCreatedTime(new Date().getTime());
        order.setStatus(0);
        order.setCarLicense("粤C 30514");
        order.setUserId("eb4b582f-274f-442f-a415-ab9272522e16");

        List<OrderDTO> orderDTOs = new ArrayList<>();
        orderDTOs.add(new OrderDTO(order));

        when(orderService.findFilterOrdersByManagerId(anyInt(), anyString())).thenReturn(orderDTOs);

        ResultActions resultActions = mvc.perform(get("/orders")
            .param("status", "0").param("carLicense", ""));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", is(hasSize(orderDTOs.size()))))
                .andExpect(jsonPath("$[0].status", is(orderDTOs.get(0).getStatus())))
                .andExpect(jsonPath("$[0].carLicense", is(orderDTOs.get(0).getCarLicense())))
                .andExpect(jsonPath("$[0].orderId", is(orderDTOs.get(0).getOrderId())));
    }
}
