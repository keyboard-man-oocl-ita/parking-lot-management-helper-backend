package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repositoryJPATest;

import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.model.ParkingLot;
import oocl.ita.keyboardman.parkinglotmanagerhelperbackend.repository.ParkingLotRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParkingLotRepositoryTest {
    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Test
    void should_save_parking_lot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(10);
        parkingLot.setCreateBy("123456");

        ParkingLot result = parkingLotRepository.save(parkingLot);

        assertNotNull(result.getParkingLotId());
    }

    @Test
    void should_page_parking_lot() {
        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setCapacity(10);
        parkingLot1.setCreateBy("123456");

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setCapacity(9);
        parkingLot2.setCreateBy("123");

        parkingLotRepository.save(parkingLot1);
        parkingLotRepository.save(parkingLot2);

        Pageable pageable = PageRequest.of(0,2, Sort.Direction.DESC,"capacity");
        Page<ParkingLot> parkingLots = parkingLotRepository.findAll(pageable);

        assertEquals(2,parkingLots.getContent().size());
    }

    @Test
    void should_find_parking_lot_by_createBy() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(10);
        parkingLot.setCreateBy("123456");
        ParkingLot parkingLot1 = parkingLotRepository.save(parkingLot);

        ParkingLot result = parkingLotRepository.findByCreateBy(parkingLot.getCreateBy()).get(0);
        assertEquals(parkingLot1.getParkingLotId(),result.getParkingLotId());
    }

    @Test
    void should_find_parking_lot_by_id() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(10);
        parkingLot.setCreateBy("123456");
        ParkingLot parkingLot1 = parkingLotRepository.save(parkingLot);

        ParkingLot result = parkingLotRepository.findByParkingLotId(parkingLot1.getParkingLotId());

        assertEquals(10,result.getCapacity());
        assertEquals("123456",result.getCreateBy());
    }
}
