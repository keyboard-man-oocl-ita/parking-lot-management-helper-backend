package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.OrderDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;

import java.util.List;

public interface OrderService {

    List<OrderDTO> findGrabOrderByParkingBoyId();


    OrderDTO updateOrder(Order order) throws Exception;

    OrderDTO updateOrderStatus(String orderId, Order order);

    List<OrderDTO> findOrderByClerkIdAndStatus(int status);

    OrderDTO createOrder(Order order);

    List<OrderDTO> findOrderByUserId();

    List<OrderDTO> findFilterOrdersByManagerId(int status, String carLicense);

    OrderDTO updateOrderWhenAppoint(Order order);
}
