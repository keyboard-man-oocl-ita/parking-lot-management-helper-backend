package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.dto.OrderDTO;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findAllByClerkId(String clerkId);

    List<Order> findByParkingLotId(String parkingLotId);

    List<Order> findByStatus(int Status);

    List<Order> findByParkingLotIdAndStatus(String paringLotId,int status);

    Order findByOrderId(String orderId);

    List<Order> findByUserIdOrderByStatus(String userId);

    List<Order> findByUserId(String userId);
}
