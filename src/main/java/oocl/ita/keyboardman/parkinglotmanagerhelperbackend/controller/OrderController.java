package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.controller;

import com.itmuch.lightsecurity.annotation.PreAuthorize;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.OrderDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders", params = {"status"})
    public List<OrderDTO> findActiveOrdersByClerkId(@RequestParam int status) {
        return orderService.findOrderByClerkIdAndStatus(status);
    }

    @PreAuthorize("hasRole('clerk')")
    @GetMapping(value = "/clerks/orders")
    public List<OrderDTO> findGrabOrdersByParkingBoyId() {
        return orderService.findGrabOrderByParkingBoyId();
    }

    @PreAuthorize("hasAllRoles('user')")
    @GetMapping(value = "/users/orders")
    public List<OrderDTO> findOrdersByUserId() {
        return orderService.findOrderByUserId();
    }


    @PutMapping(value = "/orders")
    public OrderDTO updateOrder(@RequestBody Order order) throws Exception {
        return orderService.updateOrder(order);
    }

    @PatchMapping(value = "/orders/{orderId}")
    public OrderDTO updateOrderStatus(@PathVariable String orderId, @RequestBody Order order) {
        return orderService.updateOrderStatus(orderId, order);
    }

    @PreAuthorize("hasAllRoles('manager')")
    @PutMapping("/appointedOrders")
    public OrderDTO updateOrderWhenAppoint(@RequestBody Order order){
        return orderService.updateOrderWhenAppoint(order);
    }


    @PreAuthorize("hasAllRoles('user')")
    @PostMapping(value = "/orders")
    public OrderDTO createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PreAuthorize("hasAnyRoles('manager')")
    @GetMapping(path = "/orders", params = {"status", "carLicense"})
    public List<OrderDTO> findFilterOrdersByManagerId(@RequestParam int status, @RequestParam String carLicense) {
        return orderService.findFilterOrdersByManagerId(status, carLicense);
    }

}
