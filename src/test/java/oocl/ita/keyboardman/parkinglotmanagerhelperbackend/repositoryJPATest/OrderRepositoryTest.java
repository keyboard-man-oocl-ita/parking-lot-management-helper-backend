package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repositoryJPATest;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.Order;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    @Test
    void should_save_Order() {
        Order order = new Order();
        order.setCarLicense("123456");
        order.setCreatedTime(1234l);
        order.setEndTime(456413465l);
        order.setStatus(0);
        order.setUserId("13456");
        order.setParkingLotId("1");

        Order result = orderRepository.save(order);

        assertEquals(order.getOrderId(),result.getOrderId());
    }

    @Test
    void should_page_order() {
        Order order1 = new Order();
        order1.setCarLicense("123456");
        order1.setCreatedTime(1234l);
        order1.setEndTime(456413465l);
        order1.setStatus(0);
        order1.setUserId("13456");
        order1.setParkingLotId("1");

        Order order2 = new Order();
        order2.setCarLicense("147258");
        order2.setCreatedTime(12342);
        order2.setEndTime(456413465l);
        order2.setStatus(0);
        order2.setUserId("13456");
        order2.setParkingLotId("1");

        orderRepository.save(order1);
        orderRepository.save(order2);

        Pageable pageable = PageRequest.of(0,2,Sort.Direction.DESC,"createdTime");

        Page<Order> orders = orderRepository.findAll(pageable);
        assertEquals(2,orders.getContent().size());
    }

}
