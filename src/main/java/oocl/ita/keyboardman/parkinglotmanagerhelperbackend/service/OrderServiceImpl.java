package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.service;

import com.itmuch.lightsecurity.jwt.UserOperator;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.OrderDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.exception.NoAuthException;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Clerk;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.ParkingLot;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.User;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.ClerkRepository;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.OrderRepository;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.ParkingLotRepository;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.UserRepository;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.OrderStatusConfig.FINISHED;
import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.OrderStatusConfig.PARKED;
import static oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils.OrderStatusConfig.SNATCHING;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    RedisClient redisClient;
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClerkRepository clerkRepository;

    @Autowired
    private UserOperator operator;

    @Override
    public List<OrderDTO> findOrderByClerkIdAndStatus(int status) {
        String clerkId = operator.getUser().getUsername();
        List<OrderDTO> orderDTOs;
        if (status != FINISHED) {
            List<Order> orders = orderRepository.findAllByClerkId(clerkId).stream().filter(n -> n.getStatus() != FINISHED).collect(Collectors.toList());
            orderDTOs = formatOrders(orders);
        } else {
            List<Order> orders = orderRepository.findAllByClerkId(clerkId).stream().filter(n -> n.getStatus() == FINISHED).collect(Collectors.toList());
            orderDTOs = formatOrders(orders);
        }
        return orderDTOs;
    }

    @Override
    public OrderDTO createOrder(Order order) {
        order.setStatus(SNATCHING);
        order.setCreatedTime(new Date().getTime());
        return new OrderDTO(orderRepository.save(order));
    }

    @Override
    public List<OrderDTO> findOrderByUserId() {
        String userId = operator.getUser().getUsername();
        return formatOrders(orderRepository.findByUserId(userId));
    }

    @Override
    public List<OrderDTO> findFilterOrdersByManagerId(int status, String carLicense) {
        final int ALL = -1;
        String managerId = operator.getUser().getUsername();
        List<Order> orders = new ArrayList<>();
        List<Clerk> parkingBoys = clerkRepository.findByManagedBy(managerId);

        orders.addAll(orderRepository.findByStatus(SNATCHING));
        
        for (Clerk parkingBoy: parkingBoys) {
            orders.addAll(orderRepository.findAllByClerkId(parkingBoy.getClerkId()));
        }
        if (status != ALL) {
            orders = orders.stream().filter(order -> order.getStatus() == status).collect(Collectors.toList());
        }
        if (!carLicense.isEmpty()) {
            orders = orders.stream().filter(order -> order.getCarLicense().equals(carLicense)).collect(Collectors.toList());
        }
        return formatOrders(orders);
    }

    @Override
    public OrderDTO updateOrderWhenAppoint(Order order) {
        return new OrderDTO(orderRepository.save(order));
    }


    private List<OrderDTO> formatOrders(List<Order> orders) {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(createOrderDTO(order));

        }
        return orderDTOs;
    }

    @Override
    public List<OrderDTO> findGrabOrderByParkingBoyId() {
        String parkingBoyId = operator.getUser().getUsername();
        List<ParkingLot> parkingLots = parkingLotRepository.findByManagedBy(parkingBoyId);

        List<OrderDTO> orderDTOs = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getCapacity() > orderRepository.findByParkingLotIdAndStatus(parkingLot.getParkingLotId(), PARKED).size()) {
                orders = orderRepository.findByStatus(SNATCHING);
                break;
            }
        }
        if (orders.size() != 0) {
            for (Order order : orders) {
                orderDTOs.add(new OrderDTO(order));
            }
        }
        return orderDTOs;
    }

    public OrderDTO updateOrder(Order order) throws Exception {
        if (operator.getUser() != null) {
            String clerkId = operator.getUser().getUsername();
            long startTime = System.currentTimeMillis();
            int timeout = 3 * 1000;
            //未抢到的情况下，30秒内继续获取锁
            while ((startTime + timeout) >= System.currentTimeMillis()) {
                try {
                    if (redisClient.setNx(order.getOrderId(), clerkId) == 1) {
                        order.setClerkId(clerkId);
                        orderRepository.save(order);
                    }
                }finally {

                    redisClient.delnx(order.getOrderId(),clerkId);

                }

            }
            return createOrderDTO(order);
        } else throw new NoAuthException();


    }

    @Override
    public OrderDTO updateOrderStatus(String orderId, Order order) {
        Order order1 = orderRepository.findByOrderId(orderId);
        order1.setStatus(order.getStatus());
        if (order1.getStatus() == FINISHED) {
            order1.setEndTime(new Date().getTime());
        }
        return new OrderDTO(orderRepository.save(order1));
    }

    public OrderDTO createOrderDTO(Order order) {
        OrderDTO orderDTO;
        User user = userRepository.findByUserId(order.getUserId());
        if (order.getParkingLotId() != null) {
            ParkingLot parkingLot = parkingLotRepository.findByParkingLotId(order.getParkingLotId());
            orderDTO = new OrderDTO(order);
            orderDTO.setParkingLotName(parkingLot.getName());
            orderDTO.setParkingLotId(parkingLot.getParkingLotId());
            orderDTO.setUserPhoneNumber(user.getPhoneNumber());
        } else {
            orderDTO = new OrderDTO(order);
            orderDTO.setUserPhoneNumber(user.getPhoneNumber());
        }
        return orderDTO;
    }

}
